package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class EmployeeEmailUpdatedByIdEvent(
    id: String = "",
    val email: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmployeeEmailUpdatedByIdEvent"
    }
}
