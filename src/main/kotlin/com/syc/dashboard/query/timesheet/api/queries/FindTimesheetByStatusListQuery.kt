package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class FindTimesheetByStatusListQuery(
    var status: List<TimesheetStatusEnum> = listOf(TimesheetStatusEnum.IN_PROGRESS),
) : TenantBaseQuery()
