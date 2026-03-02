package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum

class AdminUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    val status: AdminStatusEnum = AdminStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id, tenantId = tenantId)
