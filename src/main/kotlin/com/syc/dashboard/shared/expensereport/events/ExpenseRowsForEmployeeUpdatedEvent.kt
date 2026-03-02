package com.syc.dashboard.shared.expensereport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.expensereport.dto.ExpenseReportRowDto
import java.util.*

class ExpenseRowsForEmployeeUpdatedEvent(
    id: String = "",
    val expenseReportId: String = "",
    val expenseRowsForEmployee: MutableList<ExpenseReportRowDto> = mutableListOf(),
    val updatedOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ExpenseRowsForEmployeeUpdatedEvent"
    }
}
