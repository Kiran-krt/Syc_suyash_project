package com.syc.dashboard.shared.timesheet.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class TimesheetDayDetailsUpdatedByIdEvent(
    id: String,
    var timesheetRowId: String = "",
    var numberOfHours: Double = 0.0,
    var day: String = "",
    var comment: String = "",
    var updatedOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TimesheetDayDetailsUpdatedByIdEvent"
    }
}
