package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class RegisterTimesheetWithStartDateCommand(
    id: String = "",
    tenantId: String = "",
    var userId: String = "",
    var weekStartingDate: String = "",
    var weekEndingDate: String = "",
    var approvedByUserId: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
) : TenantBaseCommand(tenantId = tenantId, id = id)
