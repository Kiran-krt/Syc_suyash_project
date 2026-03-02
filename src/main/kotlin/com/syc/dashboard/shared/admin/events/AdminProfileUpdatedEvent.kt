package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class AdminProfileUpdatedEvent(
    id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "welcome101",
    var confirmPassword: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AdminProfileUpdatedEvent"
    }
}
