package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindTimesheetRowByIdQuery(
    val timesheetId: String = "",
) : TenantBaseQuery()
