package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class AdminEmailUpdatedByIdEvent(
    id: String = "",
    val email: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AdminEmailUpdatedByIdEvent"
    }
}
