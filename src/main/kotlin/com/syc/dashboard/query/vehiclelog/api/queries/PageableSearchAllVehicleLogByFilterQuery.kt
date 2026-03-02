package com.syc.dashboard.query.vehiclelog.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import org.springframework.data.domain.Sort

class PageableSearchAllVehicleLogByFilterQuery(
    val page: Int = 0,
    val limit: Int = 10,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
