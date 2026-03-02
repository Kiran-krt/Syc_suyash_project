package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.expensereport.dto.ExpenseReportRowDto

class UpdateExpenseRowsForSuyashCommand(
    id: String = "",
    var expenseRowsForSuyash: MutableList<ExpenseReportRowDto> = mutableListOf(),
) : TenantBaseCommand(id = id)
