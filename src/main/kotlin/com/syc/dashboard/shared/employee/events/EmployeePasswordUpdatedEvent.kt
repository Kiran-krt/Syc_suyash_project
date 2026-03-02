package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class EmployeePasswordUpdatedEvent(
    id: String,
    val password: String = "",
    val passwordUpdated: Boolean = false,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmployeePasswordUpdatedEvent"
    }
}
