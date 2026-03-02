package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.settings.entity.enums.PayrollItemStatusEnum

class AddPayrollItemCommand(
    id: String = "",
    var settingsId: String = "",
    val payrollItem: String = "",
    val payrollItemDescription: String = "",
    val payrollItemStatus: PayrollItemStatusEnum = PayrollItemStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
