package com.syc.dashboard.command.notification.mobile.api.commands

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import java.util.*

class SendMobileNotificationCommand(
    var userId: String,
    var userRole: UserRole,
    var objectId: String,
    var objectType: NotificationObjectTypeEnum,
    var message: String,
    var eventType: String,
    var status: MobileNotificationStatusEnum,
) : TenantBaseCommand(id = UUID.randomUUID().toString())
