package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum

class UpdateExpenseReportStatusCommand(
    id: String = "",
    val status: ExpenseReportStatusEnum?,
) : TenantBaseCommand(id = id)
