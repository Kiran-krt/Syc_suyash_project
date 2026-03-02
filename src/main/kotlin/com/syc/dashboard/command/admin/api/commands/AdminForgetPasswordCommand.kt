package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AdminForgetPasswordCommand(
    id: String = "",
    tenantId: String = "",
    var email: String = "",
    var password: String = "",
    var passwordText: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
