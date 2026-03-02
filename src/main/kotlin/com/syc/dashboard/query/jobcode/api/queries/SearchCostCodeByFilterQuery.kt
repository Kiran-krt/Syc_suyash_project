package com.syc.dashboard.query.jobcode.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum

class SearchCostCodeByFilterQuery(
    val code: String = "",
    var status: List<CostCodeStatusEnum>? = null,
) : TenantBaseQuery()
