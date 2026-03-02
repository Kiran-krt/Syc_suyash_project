package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.tvhgConfig.dto.PipeTypeDto
import com.syc.dashboard.query.tvhginput.entity.enums.PipeInformationStatusEnum

class PipeInformationDto(
    var id: String = "",
    var pipeNumber: String = "",
    var pipeId: String = "",
    var downstreamStructureNumber: String = "",
    var upstreamStructureNumber: String = "",
    var downstreamInvertElevation: String = "",
    var upstreamInvertElevation: String = "",
    var pipeTypeId: String = "",
    var roughnessCoefficient: String = "",
    var pipeLength: String = "",
    var intersectionAngle: String = "",
    var discharge: String = "",
    var status: PipeInformationStatusEnum = PipeInformationStatusEnum.ACTIVE,
    var createdBy: String = "",
    var pipeTypeInfo: PipeTypeDto? = null,
) : BaseDto()
