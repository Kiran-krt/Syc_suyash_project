package com.syc.dashboard.shared.vehiclelog.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum

class VehicleLogStatusUpdatedByIdEvent(
    id: String,
    var status: VehicleLogStatusEnum = VehicleLogStatusEnum.REVIEW,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "VehicleLogStatusUpdatedByIdEvent"
    }
}
