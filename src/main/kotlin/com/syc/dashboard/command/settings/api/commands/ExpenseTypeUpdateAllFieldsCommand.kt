package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.settings.entity.enums.ExpenseTypeStatusEnum

class ExpenseTypeUpdateAllFieldsCommand(
    id: String = "",
    var settingsId: String = "",
    val expenseType: String = "",
    val expenseTypeDescription: String = "",
    val expenseTypeStatus: ExpenseTypeStatusEnum = ExpenseTypeStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
