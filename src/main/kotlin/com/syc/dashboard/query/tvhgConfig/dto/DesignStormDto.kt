package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.tvhgConfig.entity.enums.DesignStormStatusEnum
import java.util.*

class DesignStormDto(
    var id: String = "",
    tenantId: String = "",
    var designStormName: String = "",
    var status: DesignStormStatusEnum = DesignStormStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseDto(tenantId = tenantId)
