package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.PipeMaterialStatusEnum

class PipeMaterialAllFieldsUpdatedEvent(
    id: String = "",
    var pipeMaterialId: String = "",
    var pipeMaterialType: String = "",
    var typeId: String = "",
    var status: PipeMaterialStatusEnum = PipeMaterialStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "PipeMaterialAllFieldsUpdatedEvent"
    }
}
