package com.syc.dashboard.query.employee.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum
import org.springframework.data.domain.Sort

class FindEmployeeByStatusQuery(
    var userId: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
