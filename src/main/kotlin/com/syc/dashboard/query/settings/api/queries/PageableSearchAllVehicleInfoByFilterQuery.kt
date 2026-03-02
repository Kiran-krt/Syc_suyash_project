package com.syc.dashboard.query.settings.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import org.springframework.data.domain.Sort

class PageableSearchAllVehicleInfoByFilterQuery(
    var vehicleName: String = "",
    val page: Int = 0,
    val limit: Int = 10,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
