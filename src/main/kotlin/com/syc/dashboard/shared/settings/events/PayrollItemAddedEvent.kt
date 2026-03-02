package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.settings.entity.enums.PayrollItemStatusEnum

class PayrollItemAddedEvent(
    id: String,
    var settingsId: String = "",
    val payrollItem: String = "",
    val payrollItemDescription: String = "",
    val payrollItemStatus: PayrollItemStatusEnum = PayrollItemStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "PayrollItemAddedEvent"
    }
}
