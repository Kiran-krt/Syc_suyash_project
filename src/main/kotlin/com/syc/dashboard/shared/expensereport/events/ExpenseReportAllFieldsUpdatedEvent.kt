package com.syc.dashboard.shared.expensereport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class ExpenseReportAllFieldsUpdatedEvent(
    id: String,
    val periodFrom: String = "",
    val periodTo: String = "",
    val description: String = "",
    val updatedOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ExpenseReportAllFieldsUpdatedEvent"
    }
}
