package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class EmployeePasswordResetEvent(
    id: String,
    val email: String = "",
    val password: String = "welcome1",
    val passwordUpdated: Boolean = false,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmployeePasswordResetEvent"
    }
}
