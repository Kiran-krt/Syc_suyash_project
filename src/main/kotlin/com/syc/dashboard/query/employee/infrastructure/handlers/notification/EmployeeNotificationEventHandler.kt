package com.syc.dashboard.query.employee.infrastructure.handlers.notification

import com.syc.dashboard.command.notification.email.api.commands.SendEmailNotificationCommand
import com.syc.dashboard.command.notification.inapp.api.commands.SendInAppNotificationCommand
import com.syc.dashboard.command.notification.mobile.api.commands.SendMobileNotificationCommand
import com.syc.dashboard.framework.common.config.TenantConfig
import com.syc.dashboard.framework.common.notification.email.EmailSender
import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.employee.dto.UserDto
import com.syc.dashboard.query.employee.repository.jpa.EmployeeRepository
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import com.syc.dashboard.shared.employee.notification.events.EmployeePasswordForgotEventNotification
import freemarker.template.Template
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer

@Service
class EmployeeNotificationEventHandler @Autowired constructor(
    private val employeeRepository: EmployeeRepository,
    private val commandDispatcher: CommandDispatcher,
    private val emailSender: EmailSender,
    private val freemarkerConfigurer: FreeMarkerConfigurer,
    private val tenantConfig: TenantConfig,
) : EventHandler {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)
    private val emailForgotPasswordTemplate = "email-forgot-password.ftlh"

    private fun on(event: EmployeePasswordForgotEventNotification) {
        val employee = employeeRepository.findByTenantIdAndId(tenantId = event.tenantId, id = event.id) ?: return

        val freemarkerTemplate: Template = freemarkerConfigurer.getConfiguration().getTemplate(emailForgotPasswordTemplate)
        val templateModel: MutableMap<String, Any> = mutableMapOf()
        templateModel["recipientName"] = employee.firstName
        templateModel["newPassword"] = event.passwordText
        templateModel["portalLink"] = tenantConfig.getPortalUrlByTenantId(event.tenantId)

        val notificationMessage = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel)

        sendNotifications(
            event = event,
            userDto = EntityDtoConversion.toDto(employee, UserDto::class),
            subject = "Suyash Portal - Password Reset",
            notificationMessage = notificationMessage,
            emailTo = employee.email,
        )
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is EmployeePasswordForgotEventNotification -> on(event)
        }
    }

    private fun sendNotifications(
        event: NotificationBaseEvent,
        userDto: UserDto,
        subject: String,
        notificationMessage: String,
        emailTo: String,
        emailCC: String = "",
    ) {
        if (event.sendToInApp) {
            commandDispatcher.send(
                SendInAppNotificationCommand(
                    userId = userDto.id,
                    userRole = userDto.role,
                    objectId = event.id,
                    objectType = NotificationObjectTypeEnum.EMPLOYEE,
                    message = notificationMessage,
                    eventType = event.javaClass.simpleName,
                ).buildCommand(event),
            )
        }

        if (event.sendToEmail) {
            emailSender.send(subject = subject, message = notificationMessage, emailTo = emailTo, emailCC = emailCC)
                .map {
                    commandDispatcher.send(
                        SendEmailNotificationCommand(
                            userId = userDto.id,
                            userRole = userDto.role,
                            objectId = event.id,
                            status = if (it) EmailNotificationStatusEnum.DELIVERED else EmailNotificationStatusEnum.FAILED,
                            objectType = NotificationObjectTypeEnum.EMPLOYEE,
                            subject = subject,
                            message = notificationMessage,
                            eventType = event.javaClass.simpleName,
                        ).buildCommand(event),
                    )
                }
                .subscribe()
        }

        if (event.sendToMobilePush) {
            // TODO send mobile push notification and update status
            commandDispatcher.send(
                SendMobileNotificationCommand(
                    userId = userDto.id,
                    userRole = userDto.role,
                    objectId = event.id,
                    status = MobileNotificationStatusEnum.SUCCESS,
                    objectType = NotificationObjectTypeEnum.TIMESHEET,
                    message = notificationMessage,
                    eventType = event.javaClass.simpleName,
                ).buildCommand(event),
            )
        }
    }
}
