package com.syc.dashboard.query.settings.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.settings.entity.enums.VehicleStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_settings_vehicle_info")
class VehicleInfo(
    @Id
    val id: String,
    var settingsId: String = "",
    var vehicleName: String = "",
    var vehicleModel: String = "",
    var vehicleNumber: String = "",
    var vehicleInsurance: Boolean = false,
    var vehicleStatus: VehicleStatusEnum = VehicleStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseEntity()
