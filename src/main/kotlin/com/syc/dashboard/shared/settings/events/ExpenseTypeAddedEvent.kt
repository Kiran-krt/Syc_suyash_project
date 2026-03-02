package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.settings.entity.enums.ExpenseTypeStatusEnum
import java.util.*

class ExpenseTypeAddedEvent(
    id: String,
    var settingsId: String = "",
    val expenseType: String = "",
    val expenseTypeDescription: String = "",
    val expenseTypeStatus: ExpenseTypeStatusEnum = ExpenseTypeStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ExpenseTypeAddedEvent"
    }
}
