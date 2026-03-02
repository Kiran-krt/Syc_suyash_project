package com.syc.dashboard.shared.expensereport.notification.events

import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum
import java.util.*

class ExpenseReportReviewedByAdminEventNotification(
    id: String = "",
    var commentsByAdmin: String = "",
    var adminSignature: String = "",
    var status: ExpenseReportStatusEnum = ExpenseReportStatusEnum.IN_PROGRESS,
    var updatedOn: Date = Date(),
) : NotificationBaseEvent(id = id) {

    companion object {
        const val EVENT_NAME = "ExpenseReportReviewedByAdminEventNotification"
    }
}
