package com.syc.dashboard.query.employee.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import java.util.*

class EmployeeVacationHoursAndDaysDto(
    var vacationDetails: List<EmployeeVacationDto> = emptyList(),
    var totalVacationCount: List<EmployeeVacationTotalCountDto> = emptyList(),
) : BaseDto()
