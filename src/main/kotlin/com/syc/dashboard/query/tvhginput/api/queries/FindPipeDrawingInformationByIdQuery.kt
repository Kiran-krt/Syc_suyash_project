package com.syc.dashboard.query.tvhginput.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindPipeDrawingInformationByIdQuery(
    val id: String = "",
    val pipeDrawingInformationId: String = "",
) : TenantBaseQuery()
