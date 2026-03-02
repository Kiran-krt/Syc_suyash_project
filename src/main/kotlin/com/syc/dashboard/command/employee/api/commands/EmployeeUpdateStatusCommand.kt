package com.syc.dashboard.command.employee.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum

class EmployeeUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    val status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id, tenantId = tenantId)
