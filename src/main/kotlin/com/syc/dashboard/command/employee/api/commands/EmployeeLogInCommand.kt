package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class EmployeeLogInCommand(
    val email: String,
    val password: String,
    val loggedIn: Boolean = true,
) : TenantBaseCommand(id = "")
