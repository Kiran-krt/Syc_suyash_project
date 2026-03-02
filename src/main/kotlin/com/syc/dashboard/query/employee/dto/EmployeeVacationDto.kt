package com.syc.dashboard.query.employee.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import java.util.*

class EmployeeVacationDto(
    var year: String = "",
    var date: String = "",
    var vacationType: String = "",
    var numberOfHours: Double = 0.0,
    var jobCode: String = "",
    var jobCodeDescription: String = "",
    var costCodeDescription: String = "",
) : BaseDto()
