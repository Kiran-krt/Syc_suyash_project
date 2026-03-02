package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum

class SubmitExpenseReportByEmployeeCommand(
    id: String = "",
    var status: ExpenseReportStatusEnum = ExpenseReportStatusEnum.IN_PROGRESS,
    var commentsByEmployee: String = "",
    var employeeSignature: String = "",
) : TenantBaseCommand(id = id)
