package com.syc.dashboard.query.settings.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.settings.entity.enums.VehicleStatusEnum
import com.syc.dashboard.query.vehiclelog.dto.VehicleLogDto

class VehicleInfoDto(
    var id: String = "",
    tenantId: String = "",
    var settingsId: String = "",
    var vehicleName: String = "",
    var vehicleModel: String = "",
    var vehicleNumber: String = "",
    var vehicleInsurance: Boolean = false,
    var vehicleStatus: VehicleStatusEnum = VehicleStatusEnum.ACTIVE,
    var vehicleLogInfo: List<VehicleLogDto> = listOf(),
) : TenantBaseDto(tenantId = tenantId)
