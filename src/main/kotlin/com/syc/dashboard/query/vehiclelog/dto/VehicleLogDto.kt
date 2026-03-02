package com.syc.dashboard.query.vehiclelog.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.settings.dto.VehicleInfoDto
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum
import java.util.*

class VehicleLogDto(
    var id: String = "",
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
    var createdOn: Date = Date(),
    var createdBy: String = "",
    var vehicleInfo: VehicleInfoDto? = null,
    var vehicleLogDocuments: List<DocumentIdDto> = listOf(),
    var createdByInfo: EmployeeDto? = null,
    var employeeInfo: EmployeeDto? = null,
    var accompany: MutableList<String> = mutableListOf(),
    var accompanyInfo: List<EmployeeDto> = listOf(),
) : TenantBaseDto(tenantId = tenantId)
