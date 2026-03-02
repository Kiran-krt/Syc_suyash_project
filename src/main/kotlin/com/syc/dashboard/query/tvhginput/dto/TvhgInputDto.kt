package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.tvhginput.entity.enums.TvhgInputStatusEnum
import java.util.*

class TvhgInputDto(
    var id: String = "",
    tenantId: String = "",
    var name: String = "",
    var description: String = "",
    var status: TvhgInputStatusEnum = TvhgInputStatusEnum.ACTIVE,
    var projectTitle: String = "",
    var createdBy: String = "",
    var createdOn: Date = Date(),
    var createdByInfo: AdminDto? = null,
    var projectInformation: ProjectInformationDto? = null,
    var structureList: MutableList<StructureInformationDto> = mutableListOf(),
    var hydrologicInformation: HydrologicInformationDto? = null,
    var pipeList: MutableList<PipeInformationDto> = mutableListOf(),
    var structureDrawingDataList: MutableList<StructureDrawingDataDto> = mutableListOf(),
    var inletControlParameterList: MutableList<InletControlParameterDto> = mutableListOf(),
    var outletDrawingInformation: OutletDrawingInformationDto? = null,
    var pipeDrawingInformationList: MutableList<PipeDrawingInformationDto> = mutableListOf(),
    var flowPathDrawingInformation: MutableList<FlowPathDrawingDtoInformationDto> = mutableListOf(),
) : TenantBaseDto(tenantId = tenantId)
