package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.DesignStormStatusEnum
import java.util.*

class AddedDesignStormTvhgConfigEvent(
    id: String,
    var designStormId: String = "",
    val designStormName: String = "",
    val status: DesignStormStatusEnum = DesignStormStatusEnum.ACTIVE,
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedDesignStormTvhgConfigEvent"
    }
}
