package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class TimesheetUpdateManagerCommentsByIdCommand(
    id: String = "",
    tenantId: String = "",
    var commentsByManager: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
) : TenantBaseCommand(id = id, tenantId = tenantId)
