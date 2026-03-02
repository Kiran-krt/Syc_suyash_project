package com.syc.dashboard.query.timesheet.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.jobcode.entity.CostCode
import com.syc.dashboard.query.jobcode.entity.JobCode
import com.syc.dashboard.query.project.dto.ProjectDto
import com.syc.dashboard.query.timesheet.dto.DayDetailsDto
import com.syc.dashboard.query.timesheet.dto.TimesheetDto
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetRowStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "q_timesheet_row")
class TimesheetRow(
    @Id
    val id: String,
    var timesheetId: String = "",
    var jobCodeId: String = "",
    var jobCodeInfo: JobCode? = null,
    var costCodeId: String? = null,
    var costCodeInfo: CostCode? = null,
    var description: String = "",
    var isNew: Boolean = false,
    var status: TimesheetRowStatusEnum = TimesheetRowStatusEnum.ACTIVE,
    var weeklyDetails: List<DayDetailsDto> = mutableListOf(),
    var timesheetInfo: TimesheetDto? = null,
    var projectInfo: ProjectDto? = null,
    var employeeInfo: EmployeeDto? = null,
) : TenantBaseEntity()
