package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AdminUpdateEmailCommand(
    id: String = "",
    tenantId: String = "",
    val email: String = "",
) : TenantBaseCommand(id = id, tenantId = tenantId)
