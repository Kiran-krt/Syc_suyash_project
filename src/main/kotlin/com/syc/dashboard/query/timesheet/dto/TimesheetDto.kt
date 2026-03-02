package com.syc.dashboard.query.timesheet.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import java.util.*

class TimesheetDto(
    var id: String = "",
    tenantId: String = "",
    var userId: String = "",
    var employeeInfo: EmployeeDto? = null,
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
    var weekStartingDate: String = "",
    var weekEndingDate: String = "",
    var approvedByUserId: String = "",
    var approvedByInfo: EmployeeDto? = null,
    var commentsByEmployee: String = "",
    var commentsByManager: String = "",
    var commentsByAdmin: String = "",
    var submittedByName: String = "",
    var createdOn: Date = Date(),
    var updatedOn: Date = Date(),
    var timesheetRows: List<TimesheetRowDto>? = null,
) : TenantBaseDto(tenantId = tenantId)
