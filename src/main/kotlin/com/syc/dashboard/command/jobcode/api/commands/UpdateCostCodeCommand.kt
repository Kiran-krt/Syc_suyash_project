package com.syc.dashboard.command.jobcode.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum

class UpdateCostCodeCommand(
    id: String = "",
    tenantId: String = "",
    var jobCodeId: String = "",
    var status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
    var description: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
