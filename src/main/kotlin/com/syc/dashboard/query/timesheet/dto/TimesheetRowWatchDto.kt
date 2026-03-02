package com.syc.dashboard.query.timesheet.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetRowStatusEnum

class TimesheetRowWatchDto(
    var id: String = "",
    tenantId: String = "",
    var timesheetId: String = "",
    var jobCodeId: String = "",
    var jobCodeInfo: JobCodeDto? = null,
    var costCodeId: String? = null,
    var costCodeInfo: CostCodeDto? = null,
    var description: String = "",
    var isNew: Boolean = false,
    var status: TimesheetRowStatusEnum = TimesheetRowStatusEnum.ACTIVE,
    var weeklyDetails: List<DayDetailsDto> = mutableListOf(),
    var employeeInfo: EmployeeDto? = null,
) : TenantBaseDto(tenantId = tenantId)
