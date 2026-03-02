package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhginput.entity.enums.TvhgInputStatusEnum

class TvhgInputStatusUpdatedEvent(
    id: String,
    var status: TvhgInputStatusEnum = TvhgInputStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TvhgInputStatusUpdatedEvent"
    }
}
