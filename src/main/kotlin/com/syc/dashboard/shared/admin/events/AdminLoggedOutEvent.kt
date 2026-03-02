package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class AdminLoggedOutEvent(
    id: String,
    val loggedIn: Boolean = false,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AdminLoggedOutEvent"
    }
}
