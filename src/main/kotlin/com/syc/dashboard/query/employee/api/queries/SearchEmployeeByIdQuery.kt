package com.syc.dashboard.query.employee.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class SearchEmployeeByIdQuery(
    var managerId: String = "",
) : TenantBaseQuery()
