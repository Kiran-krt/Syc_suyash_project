package com.syc.dashboard.query.jobcode.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import java.util.*

class CostCodeDto(
    var id: String = "",
    tenantId: String = "",
    var jobCodeId: String = "",
    var code: String = "",
    var description: String = "",
    var createdBy: String = "",
    var status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseDto(tenantId = tenantId)
