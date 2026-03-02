package com.syc.dashboard.query.employee.dto

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import java.util.*

class EmployeeDto(
    var id: String = "",
    tenantId: String = "",
    var title: String = "",
    var firstName: String = "",
    var middleName: String = "",
    var lastName: String = "",
    var employeeNumber: String = "",
    var joiningDate: Date = Date(),
    var role: UserRole = UserRole.EMPLOYEE,
    var managerId: String = "",
    var managerInfo: UserDto? = null,
    var vacations: Int = 0,
    var personalDays: Int = 0,
    var email: String = "",
    var passwordUpdated: Boolean = false,
    var status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
    var supervisorList: MutableList<String> = mutableListOf(),
) : TenantBaseDto(tenantId = tenantId)
