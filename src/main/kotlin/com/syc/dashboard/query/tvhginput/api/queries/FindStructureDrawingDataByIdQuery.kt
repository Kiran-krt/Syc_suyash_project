package com.syc.dashboard.query.tvhginput.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindStructureDrawingDataByIdQuery(
    val id: String = "",
    val structureDrawingDataId: String = "",
) : TenantBaseQuery()
