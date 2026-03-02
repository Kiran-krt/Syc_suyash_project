package com.syc.dashboard.query.timesheet.entity

import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.employee.entity.Employee
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import kotlin.collections.List

@Document(collection = "q_timesheet")
class Timesheet(
    @Id
    val id: String,
    var userId: String = "",
    var employeeInfo: Employee? = null,
    var status: TimesheetStatusEnum,
    var weekStartingDate: String = "",
    var weekEndingDate: String = "",
    var approvedByUserId: String = "",
    var approvedByInfo: BaseEntity? = null,
    var commentsByEmployee: String = "",
    var commentsByManager: String = "",
    var commentsByAdmin: String = "",
    var submittedByName: String = "",
    var createdOn: Date = Date(),
    var updatedOn: Date = Date(),
    var timesheetRows: List<TimesheetRow>? = null,
) : TenantBaseEntity()
