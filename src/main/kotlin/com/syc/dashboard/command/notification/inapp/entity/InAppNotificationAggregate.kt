package com.syc.dashboard.command.notification.inapp.entity

import com.syc.dashboard.command.notification.inapp.api.commands.InAppNotificationUpdateStatusCommand
import com.syc.dashboard.command.notification.inapp.api.commands.SendInAppNotificationCommand
import com.syc.dashboard.command.notification.inapp.exceptions.InAppNotificationStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import com.syc.dashboard.shared.notification.inapp.events.InAppNotificationSentEvent
import com.syc.dashboard.shared.notification.inapp.events.InAppNotificationStatusUpdatedEvent
import java.util.*

class InAppNotificationAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    private var userId: String = ""
    private var userRole: UserRole? = null
    private var objectId: String = ""
    private var message: String = ""
    private var eventType: String = ""
    private var objectType: NotificationObjectTypeEnum? = null
    private var status: InAppNotificationStatusEnum = InAppNotificationStatusEnum.UNREAD

    constructor(command: SendInAppNotificationCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            InAppNotificationSentEvent(
                id = command.id,
                userId = command.userId,
                userRole = command.userRole,
                objectId = command.objectId,
                objectType = command.objectType,
                message = command.message,
                eventType = command.eventType,
                status = InAppNotificationStatusEnum.UNREAD,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: InAppNotificationSentEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        userId = event.userId
        userRole = event.userRole
        objectId = event.objectId
        objectType = event.objectType
        message = event.message
        eventType = event.eventType
        status = event.status
    }

    fun updateStatus(command: InAppNotificationUpdateStatusCommand) {
        if (!active) {
            throw InAppNotificationStateChangeNotAllowedForInactiveStatusException(
                "Notification InApp Update Status Exception!",
            )
        }
        raiseEvent(
            InAppNotificationStatusUpdatedEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: InAppNotificationStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }
}
