package com.syc.dashboard.shared.timesheet.notification.events

import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import java.util.*

class TimesheetManagerCommentsUpdatedByIdEventNotification(
    id: String = "",
    var commentsByManager: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
    var updatedOn: Date = Date(),
) : NotificationBaseEvent(id = id) {

    companion object {
        const val EVENT_NAME = "TimesheetManagerCommentsUpdatedByIdEventNotification"
    }
}
