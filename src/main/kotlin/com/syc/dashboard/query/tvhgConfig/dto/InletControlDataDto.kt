package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.tvhgConfig.entity.enums.InletControlDataStatusEnum
import java.util.*

class InletControlDataDto(
    var id: String = "",
    tenantId: String = "",
    var inletId: String = "",
    var pathNumber: String = "",
    var inletControlDataName: String = "",
    var cparameter: String = "",
    var yparameter: String = "",
    var kparameter: String = "",
    var mparameter: String = "",
    var equationForm: String = "",
    var status: InletControlDataStatusEnum = InletControlDataStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseDto(tenantId = tenantId)
