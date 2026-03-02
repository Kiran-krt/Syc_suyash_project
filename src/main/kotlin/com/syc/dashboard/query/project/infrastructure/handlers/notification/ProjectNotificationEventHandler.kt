package com.syc.dashboard.query.project.infrastructure.handlers.notification

import com.syc.dashboard.command.notification.email.api.commands.SendEmailNotificationCommand
import com.syc.dashboard.framework.common.config.TenantConfig
import com.syc.dashboard.framework.common.notification.email.EmailSender
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import com.syc.dashboard.shared.project.notification.events.ProjectRegisteredEventNotification
import freemarker.template.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer
import reactor.core.publisher.Mono

@Service
class ProjectNotificationEventHandler @Autowired constructor(
    private val adminReactiveRepository: AdminReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
    private val freeMarkerConfigurer: FreeMarkerConfigurer,
    private val emailSender: EmailSender,
    private val commandDispatcher: CommandDispatcher,
    private val tenantConfig: TenantConfig,
) : EventHandler {

    private val emailProjectRegisteredTemplate = "email-project-registered.ftlh"

    private fun on(event: ProjectRegisteredEventNotification) {
        val createdBy: Mono<String> =
            adminReactiveRepository.findByTenantIdAndId(event.tenantId, event.createdBy)
                .map { "${it.firstName} ${it.lastName}" }
                .switchIfEmpty(
                    employeeReactiveRepository.findByTenantIdAndId(event.tenantId, event.createdBy)
                        .map { "${it.firstName} ${it.lastName}" },
                )
                .defaultIfEmpty("")

        createdBy.flatMapMany { createdByName ->
            adminReactiveRepository.findAllByTenantIdAndStatusIn(
                tenantId = event.tenantId,
                status = AdminStatusEnum.ACTIVE,
            )
                .map { EntityDtoConversion.toDto(it, AdminDto::class) }
                .distinct { it.email }
                .map { adminDto ->
                    sendEmailNotification(
                        event = event,
                        adminDto = adminDto,
                        projectCode = event.projectCode,
                        description = event.description,
                        status = event.status,
                        createdByName = createdByName,
                    )
                }
        }.subscribe()
    }

    private fun sendEmailNotification(
        event: NotificationBaseEvent,
        adminDto: AdminDto,
        projectCode: String,
        description: String,
        status: ProjectStatusEnum,
        createdByName: String,
    ) {
        val templateModel: MutableMap<String, Any> = mutableMapOf()

        templateModel["recipientName"] = adminDto.firstName + " " + adminDto.lastName
        templateModel["status"] = status
        templateModel["projectCode"] = projectCode
        templateModel["description"] = description
        templateModel["createdBy"] = createdByName
        templateModel["portalLink"] = tenantConfig.getPortalUrlByTenantId(event.tenantId)

        val freemarkerTemplate: Template = freeMarkerConfigurer.configuration.getTemplate(emailProjectRegisteredTemplate)
        val emailNotificationMessage =
            FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel)

        val emailSubject = "Suyash Portal – New Project Code Registered - $projectCode"

        emailSender.send(subject = emailSubject, message = emailNotificationMessage, emailTo = adminDto.email)
            .map {
                commandDispatcher.send(
                    SendEmailNotificationCommand(
                        userId = adminDto.id,
                        userRole = UserRole.ADMIN,
                        objectId = event.id,
                        status = if (it) EmailNotificationStatusEnum.DELIVERED else EmailNotificationStatusEnum.FAILED,
                        objectType = NotificationObjectTypeEnum.ADMIN,
                        subject = emailSubject,
                        message = emailNotificationMessage,
                        eventType = event.javaClass.simpleName,
                    ).buildCommand(event),
                )
            }
            .subscribe()
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is ProjectRegisteredEventNotification -> on(event)
        }
    }
}
