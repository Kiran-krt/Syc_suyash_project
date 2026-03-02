package com.syc.dashboard.query.jobcode.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import org.springframework.data.domain.Sort

class PageableFindCostCodeByJobCodeIdQuery(
    var jobCodeId: String = "",
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.ASC,
) : TenantBaseQuery()
