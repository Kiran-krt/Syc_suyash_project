package com.syc.dashboard.shared.expensereport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum
import java.util.*

class ExpenseReportAddedEvent(
    id: String,
    val periodFrom: String = "",
    val periodTo: String = "",
    val employeeId: String = "",
    val supervisorId: String = "",
    val description: String = "",
    val status: ExpenseReportStatusEnum = ExpenseReportStatusEnum.IN_PROGRESS,
    val createdDate: Date,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ExpenseReportAddedEvent"
    }
}
