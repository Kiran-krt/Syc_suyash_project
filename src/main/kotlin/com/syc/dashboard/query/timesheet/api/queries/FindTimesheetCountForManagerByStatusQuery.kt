package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class FindTimesheetCountForManagerByStatusQuery(
    var approvedByUserId: String = "",
    var statusList: List<TimesheetStatusEnum>,
) : TenantBaseQuery()
