package com.syc.dashboard.query.document.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindDocumentByIdQuery(
    val id: String = "",
) : TenantBaseQuery()
