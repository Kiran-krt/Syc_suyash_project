package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class TimesheetUpdateAdminCommentsByIdCommand(
    id: String = "",
    tenantId: String = "",
    var commentsByAdmin: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
) : TenantBaseCommand(id = id, tenantId = tenantId)
