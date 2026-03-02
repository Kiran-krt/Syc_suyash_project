package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class AdminPasswordUpdatedEvent(
    id: String,
    val password: String = "welcome1",
    val passwordUpdated: Boolean = false,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AdminPasswordUpdatedEvent"
    }
}
