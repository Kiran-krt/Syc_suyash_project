package com.syc.dashboard.command.vehiclelog.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum
import java.util.*

class VehicleLogUpdateAllFieldsCommand(
    id: String = "",
    tenantId: String = "",
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
) : TenantBaseCommand(tenantId = tenantId, id = id)
