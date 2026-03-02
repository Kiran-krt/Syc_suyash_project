package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import java.util.*

class EmployeeUpdateAllFieldsCommand(
    id: String = "",
    tenantId: String = "",
    var title: String = "",
    var firstName: String = "",
    var middleName: String = "",
    var lastName: String = "",
    var role: UserRole = UserRole.EMPLOYEE,
    var employeeNumber: String = "",
    var joiningDate: Date = Date(),
    var status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
    var managerId: String = "",
    var vacations: Int = 0,
    var personalDays: Int = 0,
    var supervisorList: MutableList<String> = mutableListOf(),
) : TenantBaseCommand(tenantId = tenantId, id = id)
