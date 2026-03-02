package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindJobCodeByPeriodFromQuery(
    val periodFrom: String = "",
) : TenantBaseQuery()
