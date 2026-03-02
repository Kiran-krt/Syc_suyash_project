package com.syc.dashboard.query.employee.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import java.util.*

class EmployeeVacationTotalCountDto(
    var year: String = "",
    var totalHours: String = "",
    var totalDays: String = "",
) : BaseDto()
