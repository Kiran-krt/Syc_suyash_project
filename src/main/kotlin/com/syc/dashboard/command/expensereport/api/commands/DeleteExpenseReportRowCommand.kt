package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class DeleteExpenseReportRowCommand(
    id: String = "",
    var expenseReportId: String = "",
) : TenantBaseCommand(id = id)
