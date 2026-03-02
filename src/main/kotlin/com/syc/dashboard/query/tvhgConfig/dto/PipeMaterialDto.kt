package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.tvhgConfig.entity.enums.PipeMaterialStatusEnum
import java.util.*

class PipeMaterialDto(
    var id: String = "",
    tenantId: String = "",
    var pipeMaterialType: String = "",
    var typeId: String = "",
    var status: PipeMaterialStatusEnum = PipeMaterialStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseDto(tenantId = tenantId)
