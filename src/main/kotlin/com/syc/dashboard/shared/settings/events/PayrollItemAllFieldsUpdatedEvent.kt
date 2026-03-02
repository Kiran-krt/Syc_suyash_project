package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.settings.entity.enums.PayrollItemStatusEnum
import java.util.*

class PayrollItemAllFieldsUpdatedEvent(
    id: String = "",
    var settingsId: String = "",
    var payrollItem: String = "",
    var payrollItemDescription: String = "",
    var payrollItemStatus: PayrollItemStatusEnum = PayrollItemStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "PayrollItemAllFieldsUpdatedEvent"
    }
}
