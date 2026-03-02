package com.syc.dashboard.query.tvhginput.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import org.springframework.data.domain.Sort

class PageableSearchAllTvhgInputQuery(
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
