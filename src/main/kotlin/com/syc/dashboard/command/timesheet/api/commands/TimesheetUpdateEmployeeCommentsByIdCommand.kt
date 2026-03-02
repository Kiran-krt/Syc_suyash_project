package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class TimesheetUpdateEmployeeCommentsByIdCommand(
    id: String = "",
    tenantId: String = "",
    var submittedByName: String = "",
    var commentsByEmployee: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
) : TenantBaseCommand(id = id, tenantId = tenantId)
