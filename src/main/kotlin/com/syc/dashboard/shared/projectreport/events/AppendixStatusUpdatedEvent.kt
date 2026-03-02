package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.projectreport.entity.enums.AppendixStatusEnum

class AppendixStatusUpdatedEvent(
    id: String,
    var appendixId: String = "",
    var status: AppendixStatusEnum = AppendixStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AppendixStatusUpdatedEvent"
    }
}
