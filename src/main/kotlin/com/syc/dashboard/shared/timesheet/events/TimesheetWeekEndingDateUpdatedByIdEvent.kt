package com.syc.dashboard.shared.timesheet.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class TimesheetWeekEndingDateUpdatedByIdEvent(
    id: String,
    var weekEndingDate: String = "",
    var updatedOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TimesheetWeekEndingDateUpdatedByIdEvent"
    }
}
