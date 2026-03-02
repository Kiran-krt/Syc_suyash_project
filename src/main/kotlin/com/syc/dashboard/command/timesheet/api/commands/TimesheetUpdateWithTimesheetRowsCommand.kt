package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.timesheet.dto.TimesheetRowDto

class TimesheetUpdateWithTimesheetRowsCommand(
    id: String = "",
    tenantId: String = "",
    var timesheetRows: List<TimesheetRowDto> = mutableListOf(),
) : TenantBaseCommand(tenantId = tenantId, id = id)
