package com.syc.dashboard.query.settings.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.settings.entity.enums.ExpenseTypeStatusEnum

class ExpenseTypeDto(
    var id: String = "",
    tenantId: String = "",
    var settingsId: String = "",
    var expenseType: String = "",
    var expenseTypeDescription: String = "",
    var expenseTypeStatus: ExpenseTypeStatusEnum = ExpenseTypeStatusEnum.ACTIVE,
) : TenantBaseDto(tenantId = tenantId)
