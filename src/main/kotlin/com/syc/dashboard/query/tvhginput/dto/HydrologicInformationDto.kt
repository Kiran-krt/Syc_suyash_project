package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import java.util.*

class HydrologicInformationDto(
    var designStormId: String = "",
    var zeroToTenMinuteDuration: String = "",
    var tenToFourtyMinuteDuration: String = "",
    var fourtyToOneHundredFiftyMinuteDuration: String = "",
    var createdBy: String = "",
    var createdOn: Date = Date(),
) : BaseDto()
