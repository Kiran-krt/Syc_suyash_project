package com.syc.dashboard.shared.expensereport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseByEnum
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportRowStatusEnum
import java.util.*

class ExpenseRowForSuyashAddedEvent(
    id: String = "",
    var expenseReportId: String = "",
    var expenseTypeId: String = "",
    var expenseAmount: Double = 0.0,
    var expenseDescription: String = "",
    var jobCodeId: String = "",
    var costCodeId: String = "",
    var expenseMileage: Double = 0.0,
    var expenseMileageRate: Double = 0.655,
    var expenseDate: String = "MM/dd/YYYY",
    var expenseReportRowStatus: ExpenseReportRowStatusEnum = ExpenseReportRowStatusEnum.ACTIVE,
    var expenseBy: ExpenseByEnum = ExpenseByEnum.SUYASH,
    var receiptNumber: String = "",
    var createdOn: Date = Date(),

) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ExpenseRowForSuyashAddedEvent"
    }
}
