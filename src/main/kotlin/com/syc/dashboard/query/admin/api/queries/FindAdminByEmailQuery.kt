package com.syc.dashboard.query.admin.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindAdminByEmailQuery(
    val email: String = "",
) : TenantBaseQuery()
