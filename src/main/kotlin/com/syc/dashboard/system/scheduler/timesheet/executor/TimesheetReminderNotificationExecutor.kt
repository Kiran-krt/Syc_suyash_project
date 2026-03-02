package com.syc.dashboard.system.scheduler.timesheet.executor

import com.syc.dashboard.command.notification.email.api.commands.SendEmailNotificationCommand
import com.syc.dashboard.framework.common.config.TenantConfig
import com.syc.dashboard.framework.common.notification.email.EmailSender
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.framework.core.scheduler.ScheduleExecuteAware
import com.syc.dashboard.query.employee.repository.jpa.EmployeeRepository
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import freemarker.template.Template
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer

@Service
class TimesheetReminderNotificationExecutor @Autowired constructor(
    @Value("\${syc.system.tenantIds:syc}")
    private val tenantIds: List<String> = listOf("syc"),
    private val commandDispatcher: CommandDispatcher,
    private val employeeRepository: EmployeeRepository,
    private val emailSender: EmailSender,
    private val freemarkerConfigurer: FreeMarkerConfigurer,
    private val tenantConfig: TenantConfig,
) : ScheduleExecuteAware {

    private val log = LoggerFactory.getLogger(javaClass)
    private val emailTimeSheetReminderTemplate = "email-timesheet-reminder.ftlh"

    override fun execute() {
        tenantIds.forEach { tenantId ->
            log.info("Executing timesheet reminder notifications for tenant: $tenantId")

            val activeEmployees = employeeRepository.findAllByTenantIdAndStatus(tenantId = tenantId)
            val subject = "Suyash Portal - Timesheet submit reminder"
            val notificationMessage = "Verify and submit your timesheet for this week on time <i>(by Friday EOD)</i>.\n" +
                "If already submitted, ignore this message."
            val freemarkerTemplate: Template =
                freemarkerConfigurer.configuration.getTemplate(emailTimeSheetReminderTemplate)

            val portalUrl = tenantConfig.getPortalUrlByTenantId(tenantId = tenantId)
            if (portalUrl.isEmpty()) {
                log.warn("Tenant id not configured $tenantId.")
                return
            }

            activeEmployees.forEach { employee ->
                val templateModel: MutableMap<String, Any> = mutableMapOf()
                templateModel["recipientName"] = employee.firstName
                templateModel["timesheetReminderMessage"] = notificationMessage.replace("\n", "<br />")
                templateModel["portalLink"] = portalUrl

                val emailNotificationMessage =
                    FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel)
                val emailTo = employee.email

                emailSender.send(subject = subject, message = emailNotificationMessage, emailTo = emailTo)
                    .map { emailSent ->
                        val status =
                            if (emailSent) EmailNotificationStatusEnum.DELIVERED else EmailNotificationStatusEnum.FAILED
                        val command = SendEmailNotificationCommand(
                            userId = employee.id,
                            userRole = UserRole.EMPLOYEE,
                            objectId = employee.id,
                            status = status,
                            objectType = NotificationObjectTypeEnum.SYSTEM,
                            subject = subject,
                            message = notificationMessage,
                            eventType = "TimesheetReminderNotification",
                        )
                        command.tenantId = tenantId
                        command.remoteAddress = "SYSTEM"
                        command.remoteHostName = "SYSTEM"
                        command.triggeredBy = UserRole.SYSTEM.name
                        commandDispatcher.send(command)
                    }
                    .doOnError { error ->
                        log.error("Failed to send timesheet reminder to employee: ${employee.id}", error)
                    }
                    .subscribe()
            }
            log.info("Completed timesheet reminder notifications")
        }
    }
}
