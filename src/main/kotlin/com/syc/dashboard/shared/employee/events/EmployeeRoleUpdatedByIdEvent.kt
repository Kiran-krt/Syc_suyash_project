package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.events.TenantBaseEvent

class EmployeeRoleUpdatedByIdEvent(
    id: String = "",
    val role: UserRole = UserRole.EMPLOYEE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmployeeRoleUpdatedByIdEvent"
    }
}
