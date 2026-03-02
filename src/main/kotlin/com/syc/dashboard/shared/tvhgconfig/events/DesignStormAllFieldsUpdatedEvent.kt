package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.DesignStormStatusEnum

class DesignStormAllFieldsUpdatedEvent(
    id: String = "",
    var designStormId: String = "",
    var designStormName: String = "",
    var status: DesignStormStatusEnum = DesignStormStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "DesignStormAllFieldsUpdatedEvent"
    }
}
