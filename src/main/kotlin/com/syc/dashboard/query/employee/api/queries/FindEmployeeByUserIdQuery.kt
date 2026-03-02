package com.syc.dashboard.query.employee.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindEmployeeByUserIdQuery(
    val id: String = "",
) : TenantBaseQuery()
