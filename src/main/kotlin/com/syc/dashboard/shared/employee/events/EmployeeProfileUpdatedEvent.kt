package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class EmployeeProfileUpdatedEvent(
    id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "welcome101",
    var confirmPassword: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmployeeProfileUpdatedEvent"
    }
}
