package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.timesheet.entity.enums.TimesheetStatusEnum

class RegisterTimesheetCommand(
    id: String = "",
    var userId: String = "",
    var status: TimesheetStatusEnum = TimesheetStatusEnum.IN_PROGRESS,
    var weekStartingDate: String = "",
    var weekEndingDate: String = "",
    var approvedByUserId: String = "",
    var commentsByEmployee: String = "",
    var commentsByManager: String = "",
    var commentsByAdmin: String = "",
    var submittedByName: String = "",
) : TenantBaseCommand(id = id)
