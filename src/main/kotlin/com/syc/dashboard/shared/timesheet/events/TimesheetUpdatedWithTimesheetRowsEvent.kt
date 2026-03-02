package com.syc.dashboard.shared.timesheet.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.timesheet.dto.TimesheetRowDto
import java.util.*

class TimesheetUpdatedWithTimesheetRowsEvent(
    id: String = "",
    val timesheetRowId: String = "",
    val timesheetRows: List<TimesheetRowDto> = mutableListOf(),
    val updatedOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TimesheetUpdatedWithTimesheetRowsEvent"
    }
}
