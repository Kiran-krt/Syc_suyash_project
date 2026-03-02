package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.tvhgConfig.dto.StructureTypeDto
import com.syc.dashboard.query.tvhginput.entity.enums.StructureInformationStatusEnum
import java.util.*

class StructureInformationDto(
    var id: String = "",
    tenantId: String = "",
    var structureNumber: String = "",
    var structureId: String = "",
    var structureTypeId: String = "",
    var overflowElevation: String = "",
    var contributionArea: String = "",
    var runoffCoefficient: String = "",
    var timeOfConcentration: String = "",
    var status: StructureInformationStatusEnum = StructureInformationStatusEnum.ACTIVE,
    var createdBy: String = "",
    var createdOn: Date = Date(),
    var structureTypeInfo: StructureTypeDto? = null,
) : TenantBaseDto(tenantId = tenantId)
