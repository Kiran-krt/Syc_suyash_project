package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.UnitStatusEnum
import java.util.*

class AddedUnitsInTvhgConfigEvent(
    id: String,
    var unitId: String = "",
    val unitName: String = "",
    val status: UnitStatusEnum = UnitStatusEnum.ACTIVE,
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedUnitsInTvhgConfigEvent"
    }
}
