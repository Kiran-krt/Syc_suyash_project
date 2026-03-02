package com.syc.dashboard.command.notification.email.entity

import com.syc.dashboard.command.notification.email.api.commands.SendEmailNotificationCommand
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import com.syc.dashboard.shared.notification.email.events.EmailNotificationSentEvent
import java.util.*

class EmailNotificationAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    private var userId: String = ""
    private var userRole: UserRole? = null
    private var objectId: String = ""
    private var subject: String = ""
    private var message: String = ""
    private var eventType: String = ""
    private var objectType: NotificationObjectTypeEnum? = null
    private var status: EmailNotificationStatusEnum? = null

    constructor(command: SendEmailNotificationCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            EmailNotificationSentEvent(
                id = command.id,
                userId = command.userId,
                userRole = command.userRole,
                objectId = command.objectId,
                objectType = command.objectType,
                subject = command.subject,
                message = command.message,
                eventType = command.eventType,
                status = command.status,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: EmailNotificationSentEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        userId = event.userId
        userRole = event.userRole
        objectId = event.objectId
        objectType = event.objectType
        subject = event.subject
        message = event.message
        eventType = event.eventType
        status = event.status
    }
}
