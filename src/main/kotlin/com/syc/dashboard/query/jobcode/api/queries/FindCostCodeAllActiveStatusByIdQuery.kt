package com.syc.dashboard.query.jobcode.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindCostCodeAllActiveStatusByIdQuery(
    var id: String = "",
) : TenantBaseQuery()
