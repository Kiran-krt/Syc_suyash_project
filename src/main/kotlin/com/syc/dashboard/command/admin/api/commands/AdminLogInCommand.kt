package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AdminLogInCommand(
    val email: String,
    val password: String,
    val loggedIn: Boolean = true,
) : TenantBaseCommand(id = "")
