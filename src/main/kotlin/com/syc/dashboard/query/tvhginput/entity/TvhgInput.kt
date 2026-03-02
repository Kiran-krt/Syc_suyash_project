package com.syc.dashboard.query.tvhginput.entity

import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.tvhginput.dto.*
import com.syc.dashboard.query.tvhginput.entity.enums.TvhgInputStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_tvhg_input")
class TvhgInput(
    @Id
    var id: String,
    var name: String = "",
    var description: String = "",
    var status: TvhgInputStatusEnum = TvhgInputStatusEnum.ACTIVE,
    var projectTitle: String = "",
    var projectInformation: ProjectInformationDto? = null,
    var createdBy: String = "",
    var structureList: MutableList<StructureInformationDto> = mutableListOf(),
    var createdOn: Date = Date(),
    var createdByInfo: BaseEntity? = null,
    var hydrologicInformation: HydrologicInformationDto? = null,
    var pipeList: MutableList<PipeInformationDto> = mutableListOf(),
    var structureDrawingDataList: MutableList<StructureDrawingDataDto> = mutableListOf(),
    var inletControlParameterList: MutableList<InletControlParameterDto> = mutableListOf(),
    var outletDrawingInformation: OutletDrawingInformationDto? = null,
    var pipeDrawingInformationList: MutableList<PipeDrawingInformationDto> = mutableListOf(),
    var flowPathDrawingInformation: MutableList<FlowPathDrawingDtoInformationDto> = mutableListOf(),
) : TenantBaseEntity()
