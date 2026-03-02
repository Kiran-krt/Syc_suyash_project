package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhginput.entity.enums.TvhgInputStatusEnum

class TvhgInputAllFieldsUpdatedEvent(
    id: String,
    val name: String = "",
    val description: String = "",
    val status: TvhgInputStatusEnum = TvhgInputStatusEnum.ACTIVE,
    var projectTitle: String = "",
    val createdBy: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TvhgInputAllFieldsUpdatedEvent"
    }
}
