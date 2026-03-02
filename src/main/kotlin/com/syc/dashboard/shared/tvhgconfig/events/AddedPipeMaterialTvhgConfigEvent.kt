package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.PipeMaterialStatusEnum
import java.util.*

class AddedPipeMaterialTvhgConfigEvent(
    id: String,
    var pipeMaterialId: String = "",
    var pipeMaterialType: String = "",
    var typeId: String = "",
    var status: PipeMaterialStatusEnum = PipeMaterialStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedPipeMaterialTvhgConfigEvent"
    }
}
