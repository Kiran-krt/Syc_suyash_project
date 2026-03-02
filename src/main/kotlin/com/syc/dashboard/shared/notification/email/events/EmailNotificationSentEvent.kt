package com.syc.dashboard.shared.notification.email.events

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import java.util.*

class EmailNotificationSentEvent(
    id: String,
    val userId: String,
    val userRole: UserRole,
    val objectId: String,
    val objectType: NotificationObjectTypeEnum,
    val subject: String = "",
    val message: String,
    val eventType: String,
    val status: EmailNotificationStatusEnum,
    val createdDate: Date,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmailNotificationSentEvent"
    }
}
