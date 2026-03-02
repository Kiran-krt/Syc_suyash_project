package com.syc.dashboard.command.jobcode.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import java.util.*

class AddCostCodeCommand(
    id: String = "",
    tenantId: String = "",
    var jobCodeId: String = "",
    val code: String,
    val description: String = "",
    val createdBy: String = "",
    val status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseCommand(tenantId = tenantId, id = id)
