package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AdminProfileUpdateCommand(
    id: String = "",
    tenantId: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "welcome101",
    var confirmPassword: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
