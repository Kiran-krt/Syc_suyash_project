package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.settings.entity.enums.VehicleStatusEnum

class VehicleInfoRegisteredEvent(
    id: String,
    var settingsId: String = "",
    val vehicleName: String = "",
    val vehicleModel: String = "",
    val vehicleNumber: String = "",
    val vehicleInsurance: Boolean = false,
    val vehicleStatus: VehicleStatusEnum = VehicleStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "VehicleInfoRegisteredEvent"
    }
}
