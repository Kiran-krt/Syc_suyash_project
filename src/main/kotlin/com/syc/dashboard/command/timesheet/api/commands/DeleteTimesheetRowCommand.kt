package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetRowStatusEnum

class DeleteTimesheetRowCommand(
    id: String = "",
    tenantId: String = "",
    var timesheetId: String = "",
    var status: TimesheetRowStatusEnum = TimesheetRowStatusEnum.ACTIVE,
) : TenantBaseCommand(tenantId = tenantId, id = id)
