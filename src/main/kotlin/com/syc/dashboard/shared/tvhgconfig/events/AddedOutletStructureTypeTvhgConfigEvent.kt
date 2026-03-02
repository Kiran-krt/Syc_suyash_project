package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.OutletStructureTypeStatusEnum
import java.util.*

class AddedOutletStructureTypeTvhgConfigEvent(
    id: String,
    var outletStructureTypeId: String = "",
    var outletStructureType: String = "",
    var status: OutletStructureTypeStatusEnum = OutletStructureTypeStatusEnum.ACTIVE,
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedOutletStructureTypeTvhgConfigEvent"
    }
}
