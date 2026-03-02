package com.syc.dashboard.query.project.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindProjectByIdQuery(
    val id: String = "",
) : TenantBaseQuery()
