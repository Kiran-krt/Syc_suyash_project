package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum

class EmployeeStatusUpdatedByIdEvent(
    id: String = "",
    val status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "EmployeeStatusUpdatedByIdEvent"
    }
}
