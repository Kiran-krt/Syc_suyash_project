package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class AdminFullNameUpdatedEvent(
    id: String,
    val firstName: String = "",
    val lastName: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AdminFullNameUpdatedEvent"
    }
}
