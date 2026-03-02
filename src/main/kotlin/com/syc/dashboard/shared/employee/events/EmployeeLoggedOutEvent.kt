package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class EmployeeLoggedOutEvent(
    id: String,
    val loggedIn: Boolean = false,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmployeeLoggedOutEvent"
    }
}
