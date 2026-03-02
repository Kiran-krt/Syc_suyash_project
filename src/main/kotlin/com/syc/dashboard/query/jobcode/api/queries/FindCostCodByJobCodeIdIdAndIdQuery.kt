package com.syc.dashboard.query.jobcode.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindCostCodByJobCodeIdIdAndIdQuery(
    val jobCodeId: String = "",
    val id: String = "",
) : TenantBaseQuery()
