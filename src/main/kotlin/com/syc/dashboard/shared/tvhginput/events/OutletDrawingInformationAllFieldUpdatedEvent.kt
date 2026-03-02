package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class OutletDrawingInformationAllFieldUpdatedEvent(
    id: String,
    val fieldName: String = "",
    val fieldValue: Any? = null,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "OutletDrawingInformationAllFieldUpdatedEvent"
    }
}
