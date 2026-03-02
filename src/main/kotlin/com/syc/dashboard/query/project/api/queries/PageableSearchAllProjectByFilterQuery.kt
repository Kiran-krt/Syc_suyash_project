package com.syc.dashboard.query.project.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import org.springframework.data.domain.Sort

class PageableSearchAllProjectByFilterQuery(
    val projectCode: String = "",
    var status: List<ProjectStatusEnum> = listOf(),
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.DESC,
    var sortBy: String = "projectCode",
) : TenantBaseQuery()
