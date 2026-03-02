package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AdminUpdatePasswordCommand(
    id: String = "",
    tenantId: String = "",
    var password: String = "welcome1",
) : TenantBaseCommand(tenantId = tenantId, id = id)
