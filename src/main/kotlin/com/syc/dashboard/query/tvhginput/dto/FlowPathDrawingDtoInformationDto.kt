package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.tvhgConfig.dto.InletControlDataDto
import java.util.*

class FlowPathDrawingDtoInformationDto(
    var id: String = "",
    tenantId: String = "",
    var inletControlDataId: String = "",
    var pathTitle: String = "",
    var createdOn: Date = Date(),
    var inletControlDataInfo: InletControlDataDto? = null,
) : TenantBaseDto(tenantId = tenantId)
