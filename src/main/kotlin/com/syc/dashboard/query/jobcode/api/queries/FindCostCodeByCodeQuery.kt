package com.syc.dashboard.query.jobcode.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindCostCodeByCodeQuery(
    val code: String = "",
    val jobCodeId: String = "",
) : TenantBaseQuery()
