package com.syc.dashboard.shared.systemconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class SystemConfigRegisteredEvent(
    id: String,
    val createdOn: Date,
) : TenantBaseEvent(
    id = id,
    version = -1,
) {

    companion object {
        const val EVENT_NAME = "SystemConfigRegisteredEvent"
    }
}
