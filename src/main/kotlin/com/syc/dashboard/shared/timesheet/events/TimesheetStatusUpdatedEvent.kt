package com.syc.dashboard.shared.timesheet.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import java.util.*

class TimesheetStatusUpdatedEvent(
    id: String,
    val status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
    val updatedOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TimesheetStatusUpdatedEvent"
    }
}
