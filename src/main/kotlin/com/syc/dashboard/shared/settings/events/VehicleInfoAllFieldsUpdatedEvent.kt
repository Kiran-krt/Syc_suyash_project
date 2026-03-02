package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.settings.entity.enums.VehicleStatusEnum

class VehicleInfoAllFieldsUpdatedEvent(
    id: String = "",
    var settingsId: String = "",
    var vehicleName: String = "",
    var vehicleModel: String = "",
    var vehicleNumber: String = "",
    var vehicleInsurance: Boolean = false,
    var vehicleStatus: VehicleStatusEnum = VehicleStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "VehicleInfoAllFieldsUpdatedEvent"
    }
}
