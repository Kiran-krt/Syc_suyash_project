package com.syc.dashboard.query.notification.inapp.entity

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_notification_inapp")
class InAppNotification(
    @Id
    val id: String,
    var userId: String,
    var message: String,
    var status: InAppNotificationStatusEnum,
    val creationDate: Date,
    var userRole: UserRole,
    var objectId: String,
    var objectType: NotificationObjectTypeEnum,
    var eventType: String,
) : TenantBaseEntity()
