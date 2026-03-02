package com.syc.dashboard.command.notification.email.api.commands

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import com.syc.dashboard.query.notification.entity.enums.NotificationObjectTypeEnum
import java.util.*

class SendEmailNotificationCommand(
    var userId: String,
    var userRole: UserRole,
    var objectId: String,
    var objectType: NotificationObjectTypeEnum,
    var subject: String = "",
    var message: String,
    var eventType: String,
    var status: EmailNotificationStatusEnum,
) : TenantBaseCommand(id = UUID.randomUUID().toString())
