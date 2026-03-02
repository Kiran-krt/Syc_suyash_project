package com.syc.dashboard.shared.expensereport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum
import java.util.*

class ExpenseReportStatusUpdatedEvent(
    id: String = "",
    val status: ExpenseReportStatusEnum = ExpenseReportStatusEnum.IN_PROGRESS,
    var updatedOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ExpenseReportStatusUpdatedEvent"
    }
}
