package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import java.util.*

class OutletDrawingInformationDto(
    var outletStructureTypeId: String = "",
    var lengthOfRipRap: String = "",
    var classOfRipRap: String = "",
    var distanceElevationData: MutableList<DistanceElevationDataDto> = mutableListOf(),
    var createdOn: Date = Date(),
) : BaseDto()
