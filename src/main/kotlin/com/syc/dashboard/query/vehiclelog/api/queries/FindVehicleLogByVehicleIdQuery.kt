package com.syc.dashboard.query.vehiclelog.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindVehicleLogByVehicleIdQuery(
    val vehicleId: String = "",
) : TenantBaseQuery()
