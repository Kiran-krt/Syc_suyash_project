package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class EmployeeForgotPasswordCommand(
    id: String = "",
    tenantId: String = "",
    var email: String = "",
    var password: String = "",
    var passwordText: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
