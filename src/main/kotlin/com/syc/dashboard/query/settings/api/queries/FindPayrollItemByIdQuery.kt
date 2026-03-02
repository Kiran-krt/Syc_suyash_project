package com.syc.dashboard.query.settings.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindPayrollItemByIdQuery(
    val id: String = "",
) : TenantBaseQuery()
