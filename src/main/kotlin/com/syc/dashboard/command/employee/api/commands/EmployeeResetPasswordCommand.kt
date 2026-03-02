package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class EmployeeResetPasswordCommand(
    id: String = "",
    tenantId: String = "",
    val email: String,
) : TenantBaseCommand(tenantId = tenantId, id = id)
