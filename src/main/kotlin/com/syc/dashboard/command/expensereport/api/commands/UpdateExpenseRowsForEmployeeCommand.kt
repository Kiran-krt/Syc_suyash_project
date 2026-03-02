package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.expensereport.dto.ExpenseReportRowDto

class UpdateExpenseRowsForEmployeeCommand(
    id: String = "",
    var expenseRowsForEmployee: MutableList<ExpenseReportRowDto> = mutableListOf(),
) : TenantBaseCommand(id = id)
