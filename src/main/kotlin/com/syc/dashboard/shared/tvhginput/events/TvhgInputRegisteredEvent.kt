package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhginput.entity.enums.TvhgInputStatusEnum
import java.util.Date

class TvhgInputRegisteredEvent(
    id: String,
    val name: String,
    val description: String,
    val status: TvhgInputStatusEnum = TvhgInputStatusEnum.ACTIVE,
    var projectTitle: String = "",
    val createdBy: String,
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "TvhgInputRegisteredEvent"
    }
}
