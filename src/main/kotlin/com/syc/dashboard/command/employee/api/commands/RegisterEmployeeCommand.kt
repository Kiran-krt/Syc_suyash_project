package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import java.util.*

class RegisterEmployeeCommand(
    id: String = "",
    val title: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val employeeNumber: String = "",
    val joiningDate: Date = Date(),
    val role: UserRole = UserRole.EMPLOYEE,
    val status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
    val managerId: String = "",
    val vacations: Int = 0,
    val personalDays: Int = 0,
    var email: String = "",
    var password: String = "welcome101",
    var confirmPassword: String = "",
    val supervisorList: MutableList<String> = mutableListOf(),
) : TenantBaseCommand(id = id)
