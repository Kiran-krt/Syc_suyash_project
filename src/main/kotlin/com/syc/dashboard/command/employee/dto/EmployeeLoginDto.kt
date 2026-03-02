package com.syc.dashboard.command.employee.dto

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.TenantBaseDto

class EmployeeLoginDto(
    tenantId: String = "",
    val email: String,
    val password: String,
    val role: UserRole = UserRole.EMPLOYEE,
) : TenantBaseDto(tenantId = tenantId)
