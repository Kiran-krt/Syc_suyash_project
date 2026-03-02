package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.Date

class TvhgConfigRegisteredEvent(
    id: String,
    val createdBy: String,
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TvhgConfigRegisteredEvent"
    }
}
