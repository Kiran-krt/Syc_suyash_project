package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.projectreport.entity.enums.AppendixStatusEnum

class AppendixAllFieldsUpdatedEvent(
    id: String,
    var appendixId: String = "",
    var name: String = "",
    var order: String = "",
    var status: AppendixStatusEnum = AppendixStatusEnum.ACTIVE,
    var content: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AppendixAllFieldsUpdatedEvent"
    }
}
