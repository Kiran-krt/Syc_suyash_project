package com.syc.dashboard.query.employee.dto

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum

class UserDto(
    var id: String = "",
    tenantId: String = "",
    var title: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var employeeNumber: String = "",
    var managerId: String = "",
    var role: UserRole = UserRole.MANAGER,
    var email: String = "",
    var passwordUpdated: Boolean = false,
    var status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
) : TenantBaseDto(tenantId = tenantId)
