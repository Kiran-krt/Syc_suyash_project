package com.syc.dashboard.command.vehiclelog.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum

class RegisterVehicleLogCommand(
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
    val accompany: MutableList<String> = mutableListOf(),
) : TenantBaseCommand(id = id)
