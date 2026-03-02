package com.syc.dashboard.shared.vehiclelog.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum
import java.util.*

class VehicleLogRegisteredEvent(
    id: String = "",
    val vehicleId: String = "",
    val date: String = "",
    val startingMileage: String = "",
    val endingMileage: String = "",
    val startGasRange: String = "",
    val endGasRange: String = "",
    val gasCost: String = "",
    val jobCodeId: String = "",
    val costCodeId: String = "",
    val keysHandover: Boolean = false,
    val staffInitial: String = "",
    val insurancePresent: Boolean = false,
    val ezPassAvailable: Boolean = false,
    val tollCost: String = "",
    val gasRefilled: Boolean = false,
    val remarks: String = "",
    val fieldBagBroughtInside: Boolean = false,
    val status: VehicleLogStatusEnum = VehicleLogStatusEnum.REVIEW,
    val vehicleLogDocuments: List<DocumentIdDto> = listOf(),
    val createdBy: String = "",
    val createdOn: Date = Date(),
    val accompany: MutableList<String> = mutableListOf(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "VehicleLogRegisteredEvent"
    }
}
