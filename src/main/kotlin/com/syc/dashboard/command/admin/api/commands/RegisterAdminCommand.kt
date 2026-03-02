package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class RegisterAdminCommand(
    id: String = "",
    val title: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val dateOfBirth: String = "",
    val employeeNumber: String = "",
    val email: String = "",
    var password: String = "welcome1",
) : TenantBaseCommand(id = id)
