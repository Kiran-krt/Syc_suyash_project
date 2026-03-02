package com.syc.dashboard.shared.vehiclelog.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum
import java.util.*

class VehicleLogAllFieldsUpdatedEvent(
    id: String,
    var vehicleId: String = "",
    var date: String = "",
    var startingMileage: String = "",
    var endingMileage: String = "",
    var startGasRange: String = "",
    var endGasRange: String = "",
    var gasCost: String = "",
    var jobCodeId: String = "",
    var costCodeId: String = "",
    var keysHandover: Boolean = false,
    var staffInitial: String = "",
    var insurancePresent: Boolean = false,
    var ezPassAvailable: Boolean = false,
    var tollCost: String = "",
    var gasRefilled: Boolean = false,
    var remarks: String = "",
    var fieldBagBroughtInside: Boolean = false,
    var status: VehicleLogStatusEnum = VehicleLogStatusEnum.REVIEW,
    var vehicleLogDocuments: List<DocumentIdDto> = listOf(),
    var accompany: MutableList<String> = mutableListOf(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "VehicleLogAllFieldsUpdatedEvent"
    }
}
