package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class TimesheetUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
) : TenantBaseCommand(id = id, tenantId = tenantId)
