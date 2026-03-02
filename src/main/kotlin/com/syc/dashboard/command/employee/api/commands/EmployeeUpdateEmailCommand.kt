package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class EmployeeUpdateEmailCommand(
    id: String = "",
    tenantId: String = "",
    val email: String = "",
) : TenantBaseCommand(id = id, tenantId = tenantId)
