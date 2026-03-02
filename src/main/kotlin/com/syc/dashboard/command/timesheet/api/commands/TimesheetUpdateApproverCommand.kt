package com.syc.dashboard.command.timesheet.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class TimesheetUpdateApproverCommand(
    id: String = "",
    tenantId: String = "",
    var approvedByUserId: String = "",
) : TenantBaseCommand(id = id, tenantId = tenantId)
