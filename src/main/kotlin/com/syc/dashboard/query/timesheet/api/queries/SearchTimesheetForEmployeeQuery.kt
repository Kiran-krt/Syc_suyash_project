package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class SearchTimesheetForEmployeeQuery(
    var userId: String = "",
    var status: List<TimesheetStatusEnum>? = null,
    var weekEndingDate: String = "",
) : TenantBaseQuery()
