package com.syc.dashboard.query.notification.email.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import java.util.*

class EmailNotificationDto(
    var id: String = "",
    tenantId: String = "",
    var userId: String = "",
    var message: String = "",
    var status: EmailNotificationStatusEnum = EmailNotificationStatusEnum.DELIVERED,
    var creationDate: Date = Date(),
    var objectId: String = "",
    var objectType: NotificationObjectTypeEnum = NotificationObjectTypeEnum.TIMESHEET,
) : TenantBaseDto(tenantId = tenantId)
