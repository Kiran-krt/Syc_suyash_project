package com.syc.dashboard.query.vehiclelog.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindVehicleLogForEmployeeByIdQuery(
    val createdBy: String = "",
    val vehicleId: String = "",
) : TenantBaseQuery()
