package com.syc.dashboard.shared.timesheet.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetRowStatusEnum

class TimesheetRowDeletedEvent(
    id: String = "",
    val timesheetId: String = "",
    val status: TimesheetRowStatusEnum = TimesheetRowStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TimesheetRowDeletedEvent"
    }
}
