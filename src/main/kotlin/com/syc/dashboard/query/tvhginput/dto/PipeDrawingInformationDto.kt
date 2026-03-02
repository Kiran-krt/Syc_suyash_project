package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.query.tvhgConfig.dto.PipeMaterialDto
import java.util.*

class PipeDrawingInformationDto(
    var id: String = "",
    var pipeMaterialId: String = "",
    var pipeInformationId: String = "",
    var distanceBetweenStructures: String = "",
    var pipeMaterialInfo: PipeMaterialDto? = null,
    var pipeListInfo: PipeInformationDto? = null,
    var createdOn: Date = Date(),
) : BaseDto()
