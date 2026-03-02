package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class EmployeeUpdateProfileCommand(
    id: String = "",
    tenantId: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "welcome101",
    var confirmPassword: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
