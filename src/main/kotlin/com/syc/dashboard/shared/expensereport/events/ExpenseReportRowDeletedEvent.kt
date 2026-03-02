package com.syc.dashboard.shared.expensereport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class ExpenseReportRowDeletedEvent(
    id: String = "",
    val expenseReportId: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ExpenseReportRowDeletedEvent"
    }
}
