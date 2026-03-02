package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class SettingsUpdateTimesheetDelayInHoursCommand(
    id: String = "",
    tenantId: String = "",
    var timesheetDelayInHours: Int = 8,
) : TenantBaseCommand(id = id, tenantId = tenantId)
