package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class TimesheetUpdateWeekStartingDateCommand(
    id: String = "",
    var weekStartingDate: String = "",
) : TenantBaseCommand(id = id)
