package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum

class AddExpenseReportCommand(
    id: String = "",
    val periodFrom: String = "",
    val periodTo: String = "",
    val employeeId: String = "",
    val supervisorId: String = "",
    val description: String = "",
    val status: ExpenseReportStatusEnum = ExpenseReportStatusEnum.IN_PROGRESS,
) : TenantBaseCommand(id = id)
