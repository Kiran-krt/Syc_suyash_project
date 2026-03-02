package com.syc.dashboard.command.notification.mobile.entity

import com.syc.dashboard.command.notification.mobile.api.commands.SendMobileNotificationCommand
import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import com.syc.dashboard.shared.notification.mobile.events.MobileNotificationSentEvent
import java.util.*

class MobileNotificationAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    private var userId: String = ""
    private var userRole: UserRole? = null
    private var objectId: String = ""
    private var message: String = ""
    private var eventType: String = ""
    private var objectType: NotificationObjectTypeEnum? = null
    private var status: MobileNotificationStatusEnum? = null

    constructor(command: SendMobileNotificationCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            MobileNotificationSentEvent(
                id = command.id,
                userId = command.userId,
                userRole = command.userRole,
                objectId = command.objectId,
                objectType = command.objectType,
                message = command.message,
                eventType = command.eventType,
                status = command.status,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: MobileNotificationSentEvent) {
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
}
