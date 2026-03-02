package com.syc.dashboard.query.vehiclelog.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_vehicle_log")
class VehicleLog(
    val id: String,
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
    var createdBy: String = "",
    var createdOn: Date = Date(),
    var accompany: MutableList<String> = mutableListOf(),
) : TenantBaseEntity()
