package com.syc.dashboard.shared.timesheet.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.timesheet.dto.TimesheetRowDto
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import java.util.*

class TimesheetRegisteredEvent(
    id: String,
    var userId: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
    var weekStartingDate: String = "",
    var weekEndingDate: String = "",
    var approvedByUserId: String = "",
    var commentsByEmployee: String = "",
    var commentsByManager: String = "",
    var commentsByAdmin: String = "",
    var submittedByName: String = "",
    var timesheetRows: List<TimesheetRowDto> = mutableListOf(),
    val createdDate: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TimesheetRegisteredEvent"
    }
}
