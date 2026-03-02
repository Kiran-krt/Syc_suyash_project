package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class SettingsUpdateYearlyQuarterCommand(
    id: String = "",
    tenantId: String = "",
    var yearlyQuarterName: String = "",
    var yearlyQuarterDescription: String = "",
) : TenantBaseCommand(id = id, tenantId = tenantId)
