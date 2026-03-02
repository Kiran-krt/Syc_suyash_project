package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class SettingsUpdateTimeZoneCommand(
    id: String = "",
    tenantId: String = "",
    var timeZone: String = "EST",
) : TenantBaseCommand(id = id, tenantId = tenantId)
