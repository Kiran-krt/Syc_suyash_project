package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AdminLogOutCommand(
    id: String = "",
    tenantId: String = "",
    val loggedIn: Boolean = false,
) : TenantBaseCommand(tenantId = tenantId, id = id)
