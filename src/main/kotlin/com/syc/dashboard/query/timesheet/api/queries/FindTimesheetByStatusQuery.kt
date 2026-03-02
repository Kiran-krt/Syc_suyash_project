package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import org.springframework.data.domain.Sort

class FindTimesheetByStatusQuery(
    var status: List<TimesheetStatusEnum> = listOf(TimesheetStatusEnum.IN_PROGRESS),
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
