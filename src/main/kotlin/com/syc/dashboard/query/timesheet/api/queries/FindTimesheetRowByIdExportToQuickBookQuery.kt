package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindTimesheetRowByIdExportToQuickBookQuery(
    tenantId: String,
    val timesheetId: String = "",
) : TenantBaseQuery(tenantId = tenantId)
