package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.settings.entity.enums.VehicleStatusEnum

class RegisterVehicleInfoCommand(
    id: String = "",
    var settingsId: String = "",
    val vehicleName: String = "",
    val vehicleModel: String = "",
    val vehicleNumber: String = "",
    val vehicleInsurance: Boolean = false,
    val vehicleStatus: VehicleStatusEnum = VehicleStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
