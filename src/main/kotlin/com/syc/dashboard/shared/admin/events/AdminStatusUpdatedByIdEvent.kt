package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum

class AdminStatusUpdatedByIdEvent(
    id: String = "",
    val status: AdminStatusEnum = AdminStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AdminStatusUpdatedByIdEvent"
    }
}
