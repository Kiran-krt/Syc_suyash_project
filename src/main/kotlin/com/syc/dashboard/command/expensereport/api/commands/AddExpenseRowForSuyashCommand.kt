package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportRowStatusEnum
import java.util.*

class AddExpenseRowForSuyashCommand(
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
    var receiptNumber: String = "",
    var createdOn: Date = Date(),
) : TenantBaseCommand(id = id)
