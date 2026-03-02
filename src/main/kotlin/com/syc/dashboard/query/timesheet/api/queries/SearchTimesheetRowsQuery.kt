package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class SearchTimesheetRowsQuery(
    val jobcode: String = "",
    val startDate: String = "",
    val endDate: String = "",
) : TenantBaseQuery()
