package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum

class ReviewExpenseReportBySupervisorCommand(
    id: String = "",
    var status: ExpenseReportStatusEnum = ExpenseReportStatusEnum.IN_PROGRESS,
    var commentsBySupervisor: String = "",
    var supervisorSignature: String = "",
) : TenantBaseCommand(id = id)
