package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class ExpenseReportUpdateAllFieldsCommand(
    id: String = "",
    val periodFrom: String = "",
    val periodTo: String = "",
    val description: String = "",
) : TenantBaseCommand(id = id)
