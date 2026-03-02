package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AdminUpdateFullNameCommand(
    id: String = "",
    tenantId: String = "",
    val firstName: String = "",
    val lastName: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
