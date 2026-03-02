package com.syc.dashboard.query.tvhgConfig.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.tvhginput.dto.InletControlParameterDto
import java.util.*

class TvhgConfigDto(
    var id: String = "",
    tenantId: String = "",
    var createdBy: String = "",
    val createdOn: Date = Date(),
    var units: MutableList<UnitsDto> = mutableListOf(),
    var designStorm: MutableList<DesignStormDto> = mutableListOf(),
    var structureType: MutableList<StructureTypeDto> = mutableListOf(),
    var inletControlData: MutableList<InletControlDataDto> = mutableListOf(),
    var inletControlParameter: MutableList<InletControlParameterDto> = mutableListOf(),
    var pipeMaterialList: MutableList<PipeMaterialDto> = mutableListOf(),
    var pipeTypeList: MutableList<PipeTypeDto> = mutableListOf(),
    var outletStructureType: MutableList<OutletStructureTypeDto> = mutableListOf(),
    var mdStandardNumberList: MutableList<MdStandardNumberListDto> = mutableListOf(),
) : TenantBaseDto(tenantId = tenantId)
