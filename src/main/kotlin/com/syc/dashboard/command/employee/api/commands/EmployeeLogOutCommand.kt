package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class EmployeeLogOutCommand(
    id: String = "",
    tenantId: String = "",
    val loggedIn: Boolean = false,
) : TenantBaseCommand(tenantId = tenantId, id = id)
