package com.syc.dashboard.command.notification.inapp.api.commands

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import java.util.*

class SendInAppNotificationCommand(
    var userId: String,
    var userRole: UserRole,
    var objectId: String,
    var objectType: NotificationObjectTypeEnum,
    var message: String,
    var eventType: String,
    val status: InAppNotificationStatusEnum = InAppNotificationStatusEnum.UNREAD,
) : TenantBaseCommand(id = UUID.randomUUID().toString())
