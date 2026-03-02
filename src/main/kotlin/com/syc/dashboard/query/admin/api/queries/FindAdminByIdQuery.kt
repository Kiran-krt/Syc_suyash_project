package com.syc.dashboard.query.admin.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindAdminByIdQuery(
    val id: String = "",
) : TenantBaseQuery()
