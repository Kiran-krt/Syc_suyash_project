package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class TimesheetUpdateWeekEndingDateCommand(
    id: String = "",
    var weekEndingDate: String = "",
) : TenantBaseCommand(id = id)
