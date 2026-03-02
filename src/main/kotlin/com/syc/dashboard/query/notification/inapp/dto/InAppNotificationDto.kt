package com.syc.dashboard.query.notification.inapp.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import java.util.*

class InAppNotificationDto(
    var id: String = "",
    tenantId: String = "",
    var userId: String = "",
    var message: String = "",
    var status: InAppNotificationStatusEnum = InAppNotificationStatusEnum.UNREAD,
    var creationDate: Date = Date(),
    var objectId: String = "",
    var objectType: NotificationObjectTypeEnum = NotificationObjectTypeEnum.TIMESHEET,
) : TenantBaseDto(tenantId = tenantId)
