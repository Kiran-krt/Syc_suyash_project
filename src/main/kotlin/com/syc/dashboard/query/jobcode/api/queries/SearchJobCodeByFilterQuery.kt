package com.syc.dashboard.query.jobcode.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import org.springframework.data.domain.Sort

class SearchJobCodeByFilterQuery(
    val code: String = "",
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.ASC,
    var status: List<JobCodeStatusEnum>? = null,
    var sortBy: String = "code",
) : TenantBaseQuery()
