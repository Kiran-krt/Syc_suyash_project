package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class EmployeeUpdateRoleCommand(
    id: String = "",
    tenantId: String = "",
    val role: UserRole = UserRole.EMPLOYEE,
) : TenantBaseCommand(id = id, tenantId = tenantId)
