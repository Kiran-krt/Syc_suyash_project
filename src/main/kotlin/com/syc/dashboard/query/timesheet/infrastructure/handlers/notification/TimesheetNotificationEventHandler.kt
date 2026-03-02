package com.syc.dashboard.query.timesheet.infrastructure.handlers.notification

import com.syc.dashboard.command.notification.email.api.commands.SendEmailNotificationCommand
import com.syc.dashboard.command.notification.inapp.api.commands.SendInAppNotificationCommand
import com.syc.dashboard.command.notification.mobile.api.commands.SendMobileNotificationCommand
import com.syc.dashboard.framework.common.config.TenantConfig
import com.syc.dashboard.framework.common.notification.email.EmailSender
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum
import com.syc.dashboard.query.admin.repository.jpa.AdminRepository
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.employee.dto.UserDto
import com.syc.dashboard.query.employee.repository.jpa.EmployeeRepository
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import com.syc.dashboard.query.timesheet.entity.Timesheet
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import com.syc.dashboard.query.timesheet.repository.jpa.TimesheetRepository
import com.syc.dashboard.shared.timesheet.notification.events.TimesheetAdminCommentsUpdatedByIdEventNotification
import com.syc.dashboard.shared.timesheet.notification.events.TimesheetEmployeeCommentsUpdatedByIdEventNotification
import com.syc.dashboard.shared.timesheet.notification.events.TimesheetManagerCommentsUpdatedByIdEventNotification
import freemarker.template.Template
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux

@Service
class TimesheetNotificationEventHandler @Autowired constructor(
    private val timesheetRepository: TimesheetRepository,
    private val adminRepository: AdminRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
    private val employeeRepository: EmployeeRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
    private val emailSender: EmailSender,
    private val freemarkerConfigurer: FreeMarkerConfigurer,
    private val tenantConfig: TenantConfig,
    private val commandDispatcher: CommandDispatcher,
) : EventHandler {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)
    private val emailTimesheetRejectedTemplate = "email-timesheet-rejected.ftlh"

    private fun on(event: TimesheetEmployeeCommentsUpdatedByIdEventNotification) {
        val timesheet = timesheetRepository.findByTenantIdAndId(tenantId = event.tenantId, id = event.id) ?: return

        val triggeredByUserDto = getTriggeredByUserDto(event.tenantId, event.triggeredBy) ?: return

        val notificationMessage =
            notificationMessage(timesheet, event.status, triggeredByUserDto, event.commentsByEmployee)

        // list of notification users - employee, approved by (manager), all admins
        val notificationUserList = mutableSetOf<String>()
        notificationUserList.add(timesheet.userId)
        notificationUserList.add(timesheet.approvedByUserId)

        if (event.sendToAdminInApp) {
            notificationUserList.addAll(
                adminRepository.findListOfIdsByTenantIdAndStatus(
                    tenantId = event.tenantId,
                    status = AdminStatusEnum.ACTIVE,
                ),
            )
        }
        notificationUserList.toFlux()
            .flatMap { notificationUserId ->
                val userDetails: Mono<UserDto> =
                    employeeReactiveRepository.findByTenantIdAndId(
                        tenantId = event.tenantId,
                        id = notificationUserId,
                    )
                        .map { EntityDtoConversion.toDto(it, UserDto::class) }
                        .switchIfEmpty {
                            adminReactiveRepository.findByTenantIdAndId(
                                tenantId = event.tenantId,
                                id = notificationUserId,
                            )
                                .map { EntityDtoConversion.toDto(it, UserDto::class) }
                        }
                userDetails
            }
            .map {
                sendNotifications(event = event, userDto = it, notificationMessage = notificationMessage, timesheet = timesheet)
            }
            .subscribe()
    }

    private fun on(event: TimesheetManagerCommentsUpdatedByIdEventNotification) {
        val timesheet = timesheetRepository.findByTenantIdAndId(tenantId = event.tenantId, id = event.id) ?: return

        val triggeredByUserDto = getTriggeredByUserDto(event.tenantId, event.triggeredBy) ?: return

        val notificationMessage =
            notificationMessage(timesheet, event.status, triggeredByUserDto, event.commentsByManager)

        // list of notification users - employee, approved by (manager), all admins
        val notificationUserList = mutableSetOf<String>()
        notificationUserList.add(timesheet.userId)
        notificationUserList.add(timesheet.approvedByUserId)

        if (event.sendToAdminInApp) {
            notificationUserList.addAll(
                adminRepository.findListOfIdsByTenantIdAndStatus(
                    tenantId = event.tenantId,
                    status = AdminStatusEnum.ACTIVE,
                ),
            )
        }
        notificationUserList.toFlux()
            .flatMap { notificationUserId ->
                val userDetails: Mono<UserDto> =
                    employeeReactiveRepository.findByTenantIdAndId(tenantId = event.tenantId, id = notificationUserId)
                        .map { EntityDtoConversion.toDto(it, UserDto::class) }
                        .switchIfEmpty {
                            adminReactiveRepository.findByTenantIdAndId(
                                tenantId = event.tenantId,
                                id = notificationUserId,
                            )
                                .map { EntityDtoConversion.toDto(it, UserDto::class) }
                        }
                userDetails
            }
            .map {
                sendNotifications(event = event, userDto = it, notificationMessage = notificationMessage, timesheet = timesheet)
            }
            .subscribe()
    }

    private fun on(event: TimesheetAdminCommentsUpdatedByIdEventNotification) {
        val timesheet = timesheetRepository.findByTenantIdAndId(tenantId = event.tenantId, id = event.id) ?: return

        val triggeredByUserDto = getTriggeredByUserDto(event.tenantId, event.triggeredBy) ?: return

        val notificationMessage =
            notificationMessage(timesheet, event.status, triggeredByUserDto, event.commentsByAdmin)

        // list of notification users - employee, approved by (manager), all admins
        val notificationUserList = mutableSetOf<String>()
        notificationUserList.add(timesheet.userId)
        notificationUserList.add(timesheet.approvedByUserId)

        if (event.sendToAdminInApp) {
            notificationUserList.addAll(
                adminRepository.findListOfIdsByTenantIdAndStatus(
                    tenantId = event.tenantId,
                    status = AdminStatusEnum.ACTIVE,
                ),
            )
        }

        notificationUserList.toFlux()
            .flatMap { notificationUserId ->
                val userDetails: Mono<UserDto> =
                    employeeReactiveRepository.findByTenantIdAndId(tenantId = event.tenantId, id = notificationUserId)
                        .map { EntityDtoConversion.toDto(it, UserDto::class) }
                        .switchIfEmpty {
                            adminReactiveRepository.findByTenantIdAndId(
                                tenantId = event.tenantId,
                                id = notificationUserId,
                            )
                                .map { EntityDtoConversion.toDto(it, UserDto::class) }
                        }
                userDetails
            }
            .map {
                sendNotifications(event = event, userDto = it, notificationMessage = notificationMessage, timesheet = timesheet)
            }
            .subscribe()
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is TimesheetEmployeeCommentsUpdatedByIdEventNotification -> on(event)
            is TimesheetManagerCommentsUpdatedByIdEventNotification -> on(event)
            is TimesheetAdminCommentsUpdatedByIdEventNotification -> on(event)
        }
    }

    private fun getTriggeredByUserDto(tenantId: String, triggeredByUserId: String): UserDto? {
        if (triggeredByUserId == UserRole.SYSTEM.name) {
            return UserDto(id = triggeredByUserId, firstName = triggeredByUserId)
        }

        var triggeredByUser: BaseEntity? = employeeRepository.findByTenantIdAndId(tenantId = tenantId, id = triggeredByUserId)
        if (triggeredByUser == null) {
            triggeredByUser = adminRepository.findByTenantIdAndId(tenantId = tenantId, id = triggeredByUserId) ?: return null
        }

        return EntityDtoConversion.toDto(triggeredByUser, UserDto::class)
    }

    private fun notificationMessage(
        timesheet: Timesheet,
        status: TimesheetStatusEnum,
        triggeredByUserDto: UserDto,
        comments: String,
    ): String = "Timesheet is updated:\n" +
        "- Week Ending: ${timesheet.weekEndingDate}\n" +
        "- Employee Name: ${timesheet.employeeInfo?.firstName} ${timesheet.employeeInfo?.lastName}\n" +
        "- Employee #: ${timesheet.employeeInfo?.employeeNumber}\n" +
        "- Timesheet Status: ${status}\n" +
        "- Comments: ${comments}\n" +
        "- Updated By: ${triggeredByUserDto.firstName} ${triggeredByUserDto.lastName}"

    private fun sendNotifications(
        event: NotificationBaseEvent,
        userDto: UserDto,
        notificationMessage: String,
        timesheet: Timesheet,
    ) {
        if ((event.sendToInApp && userDto.role != UserRole.ADMIN) || (userDto.role == UserRole.ADMIN && event.sendToAdminInApp)) {
            commandDispatcher.send(
                SendInAppNotificationCommand(
                    userId = userDto.id,
                    userRole = userDto.role,
                    objectId = event.id,
                    objectType = NotificationObjectTypeEnum.TIMESHEET,
                    message = notificationMessage,
                    eventType = event.javaClass.simpleName,
                ).buildCommand(event),
            )
        }

        if ((event.sendToEmail && userDto.role != UserRole.ADMIN) || (userDto.role == UserRole.ADMIN && event.sendToAdminEmail)) {
            val status = event.javaClass.getMethod("getStatus").invoke(event) as TimesheetStatusEnum
            if (status == TimesheetStatusEnum.REJECTED_BY_ADMIN || status == TimesheetStatusEnum.REJECTED_BY_MANAGER) {
                val freemarkerTemplate: Template = freemarkerConfigurer.configuration.getTemplate(emailTimesheetRejectedTemplate)
                val templateModel: MutableMap<String, Any> = mutableMapOf()
                templateModel["recipientName"] = userDto.firstName
                templateModel["timesheetRejectedMessage"] = notificationMessage.replace("\n", "<br />")
                templateModel["portalLink"] = tenantConfig.getPortalUrlByTenantId(event.tenantId)

                val emailNotificationMessage = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel)
                val emailSubject = "Suyash Portal - Timesheet Rejected for '${timesheet.employeeInfo?.firstName} ${timesheet.employeeInfo?.lastName}' for week ending date '${timesheet.weekEndingDate}'"

                emailSender.send(subject = emailSubject, message = emailNotificationMessage, emailTo = userDto.email)
                    .map {
                        commandDispatcher.send(
                            SendEmailNotificationCommand(
                                userId = userDto.id,
                                userRole = userDto.role,
                                objectId = event.id,
                                status = if (it) EmailNotificationStatusEnum.DELIVERED else EmailNotificationStatusEnum.FAILED,
                                objectType = NotificationObjectTypeEnum.TIMESHEET,
                                subject = emailSubject,
                                message = emailNotificationMessage,
                                eventType = event.javaClass.simpleName,
                            ).buildCommand(event),
                        )
                    }
                    .subscribe()
            }
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
