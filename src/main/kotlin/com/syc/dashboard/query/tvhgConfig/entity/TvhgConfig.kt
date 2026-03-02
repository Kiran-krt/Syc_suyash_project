package com.syc.dashboard.query.tvhgConfig.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.tvhgConfig.dto.*
import com.syc.dashboard.query.tvhginput.dto.InletControlParameterDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_tvhg_config")
class TvhgConfig(
    @Id
    val id: String,
    var createdBy: String = "",
    val createdOn: Date = Date(),
    var units: MutableList<UnitsDto> = mutableListOf(),
    var designStorm: MutableList<DesignStormDto> = mutableListOf(),
    var structureType: MutableList<StructureTypeDto> = mutableListOf(),
    var inletControlData: MutableList<InletControlDataDto> = mutableListOf(),
    var inletControlParameter: MutableList<InletControlParameterDto> = mutableListOf(),
    var outletStructureType: MutableList<OutletStructureTypeDto> = mutableListOf(),
    var pipeTypeList: MutableList<PipeTypeDto> = mutableListOf(),
    var pipeMaterialList: MutableList<PipeMaterialDto> = mutableListOf(),
    var mdStandardNumberList: MutableList<MdStandardNumberListDto> = mutableListOf(),
) : TenantBaseEntity()
