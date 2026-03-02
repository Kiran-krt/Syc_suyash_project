package com.syc.dashboard.query.jobcode.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindAllActiveStatusJobCodeByIdQuery(
    var id: String = "",
) : TenantBaseQuery()
