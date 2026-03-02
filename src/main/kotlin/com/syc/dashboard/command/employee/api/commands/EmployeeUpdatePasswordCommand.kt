package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class EmployeeUpdatePasswordCommand(
    id: String = "",
    tenantId: String = "",
    var password: String = "",
    var confirmPassword: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
