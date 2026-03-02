package com.syc.dashboard.command.notification.inapp.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum

class InAppNotificationUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    val status: InAppNotificationStatusEnum = InAppNotificationStatusEnum.READ,
) : TenantBaseCommand(tenantId = tenantId, id = id)
