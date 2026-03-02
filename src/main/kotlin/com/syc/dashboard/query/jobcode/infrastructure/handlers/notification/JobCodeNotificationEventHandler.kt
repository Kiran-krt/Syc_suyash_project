package com.syc.dashboard.query.jobcode.infrastructure.handlers.notification

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
import com.syc.dashboard.query.jobcode.entity.JobCode
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import com.syc.dashboard.query.jobcode.repository.jpa.JobCodeRepository
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import com.syc.dashboard.query.project.entity.Project
import com.syc.dashboard.query.project.repository.jpa.ProjectRepository
import com.syc.dashboard.shared.jobcode.notification.events.CostCodeAddedEventNotification
import com.syc.dashboard.shared.jobcode.notification.events.JobCodeRegisteredEventNotification
import freemarker.template.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer
import reactor.core.publisher.Mono

@Service
class JobCodeNotificationEventHandler @Autowired constructor(
    private val adminReactiveRepository: AdminReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
    private val projectRepository: ProjectRepository,
    private val jobCodeRepository: JobCodeRepository,
    private val freeMarkerConfigurer: FreeMarkerConfigurer,
    private val emailSender: EmailSender,
    private val commandDispatcher: CommandDispatcher,
    private val tenantConfig: TenantConfig,
) : EventHandler {

    private val emailJobCodeRegisteredTemplate = "email-jobcode-registered.ftlh"
    private val emailCostCodeAddedTemplate = "email-costcode-added.ftlh"

    private fun on(event: JobCodeRegisteredEventNotification) {
        val createdBy: Mono<String> =
            adminReactiveRepository.findByTenantIdAndId(event.tenantId, event.createdBy)
                .map { "${it.firstName} ${it.lastName}" }
                .switchIfEmpty(
                    employeeReactiveRepository.findByTenantIdAndId(event.tenantId, event.createdBy)
                        .map { "${it.firstName} ${it.lastName}" },
                )
                .defaultIfEmpty("")

        val project = projectRepository.findByTenantIdAndId(event.tenantId, event.projectId)

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
                        jobCode = event.code,
                        project = project,
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
        jobCode: String,
        project: Project?,
        description: String,
        status: JobCodeStatusEnum,
        createdByName: String,
    ) {
        val templateModel: MutableMap<String, Any> = mutableMapOf()

        templateModel["recipientName"] = adminDto.firstName + " " + adminDto.lastName
        templateModel["status"] = status
        templateModel["projectCode"] = project?.projectCode ?: ""
        templateModel["jobCode"] = jobCode
        templateModel["description"] = description
        templateModel["createdBy"] = createdByName
        templateModel["portalLink"] = tenantConfig.getPortalUrlByTenantId(event.tenantId)

        val freemarkerTemplate: Template = freeMarkerConfigurer.configuration.getTemplate(emailJobCodeRegisteredTemplate)
        val emailNotificationMessage =
            FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel)

        val emailSubject = "Suyash Portal – New Job Code Registered $jobCode For Project Code - ${project?.projectCode}"

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

    private fun on(event: CostCodeAddedEventNotification) {
        val createdBy: Mono<String> =
            adminReactiveRepository.findByTenantIdAndId(event.tenantId, event.createdBy)
                .map { "${it.firstName} ${it.lastName}" }
                .switchIfEmpty(
                    employeeReactiveRepository.findByTenantIdAndId(event.tenantId, event.createdBy)
                        .map { "${it.firstName} ${it.lastName}" },
                )
                .defaultIfEmpty("")

        val jobCode = jobCodeRepository.findByTenantIdAndId(event.tenantId, event.jobCodeId)

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
                        jobCode = jobCode,
                        costCode = event.code,
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
        jobCode: JobCode?,
        costCode: String,
        description: String,
        status: CostCodeStatusEnum,
        createdByName: String,
    ) {
        val templateModel: MutableMap<String, Any> = mutableMapOf()

        templateModel["recipientName"] = adminDto.firstName + " " + adminDto.lastName
        templateModel["status"] = status
        templateModel["jobCode"] = jobCode?.code ?: ""
        templateModel["costCode"] = costCode
        templateModel["description"] = description
        templateModel["createdBy"] = createdByName
        templateModel["portalLink"] = tenantConfig.getPortalUrlByTenantId(event.tenantId)

        val freemarkerTemplate: Template = freeMarkerConfigurer.configuration.getTemplate(emailCostCodeAddedTemplate)
        val emailNotificationMessage =
            FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel)

        val emailSubject = "Suyash Portal – New Cost Code Added $costCode For Job Code - ${jobCode?.code}"

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
            is JobCodeRegisteredEventNotification -> on(event)
            is CostCodeAddedEventNotification -> on(event)
        }
    }
}
