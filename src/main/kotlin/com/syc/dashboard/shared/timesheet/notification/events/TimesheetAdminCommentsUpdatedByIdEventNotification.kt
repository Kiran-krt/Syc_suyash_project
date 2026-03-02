package com.syc.dashboard.shared.timesheet.notification.events

import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import java.util.*

class TimesheetAdminCommentsUpdatedByIdEventNotification(
    id: String = "",
    var commentsByAdmin: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
    var updatedOn: Date = Date(),
) : NotificationBaseEvent(id = id) {

    companion object {
        const val EVENT_NAME = "TimesheetAdminCommentsUpdatedByIdEventNotification"
    }
}
