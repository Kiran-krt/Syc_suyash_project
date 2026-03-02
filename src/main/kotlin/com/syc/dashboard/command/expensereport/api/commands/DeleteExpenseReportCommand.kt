package com.syc.dashboard.command.expensereport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class DeleteExpenseReportCommand(
    id: String = "",
) : TenantBaseCommand(id = id)
