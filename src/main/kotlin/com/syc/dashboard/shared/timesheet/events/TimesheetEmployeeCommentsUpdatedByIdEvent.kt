package com.syc.dashboard.shared.timesheet.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import java.util.*

class TimesheetEmployeeCommentsUpdatedByIdEvent(
    id: String = "",
    var submittedByName: String = "",
    var commentsByEmployee: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
    var updatedOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TimesheetEmployeeCommentsUpdatedByIdEvent"
    }
}
