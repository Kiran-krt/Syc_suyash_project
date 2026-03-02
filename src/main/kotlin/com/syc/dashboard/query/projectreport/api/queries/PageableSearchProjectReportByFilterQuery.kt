package com.syc.dashboard.query.projectreport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum
import org.springframework.data.domain.Sort

class PageableSearchProjectReportByFilterQuery(
    var projectName: String = "",
    var status: List<ProjectReportStatusEnum> = listOf(),
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
