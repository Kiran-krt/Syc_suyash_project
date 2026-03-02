package com.syc.dashboard.query.notification.mobile.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import java.util.*

class MobileNotificationDto(
    var id: String = "",
    var userId: String = "",
    var message: String = "",
    var status: MobileNotificationStatusEnum = MobileNotificationStatusEnum.SUCCESS,
    var creationDate: Date = Date(),
    var objectId: String = "",
    var objectType: NotificationObjectTypeEnum = NotificationObjectTypeEnum.TIMESHEET,
) : BaseDto()
