package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class TimesheetUpdateDayDetailsCommand(
    id: String = "",
    tenantId: String = "",
    var timesheetRowId: String = "",
    var day: String = "",
    var numberOfHours: Double = 0.0,
    var comment: String = "",
) : TenantBaseCommand(id = id, tenantId = tenantId)
