package com.syc.dashboard.query.jobcode.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_costcode")
class CostCode(
    val id: String,
    tenantId: String,
    var jobCodeId: String = "",
    var code: String = "",
    var description: String = "",
    var createdBy: String = "",
    var status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseEntity(tenantId = tenantId)
