package com.syc.dashboard.query.tvhginput.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import java.util.*

class InletControlParameterDto(
    var id: String = "",
    var boundaryConditions: String = "",
    var cparameter: String = "",
    var yparameter: String = "",
    var kparameter: String = "",
    var mparameter: String = "",
    var equationForm: String = "",
    var createdOn: Date = Date(),
) : BaseDto()
