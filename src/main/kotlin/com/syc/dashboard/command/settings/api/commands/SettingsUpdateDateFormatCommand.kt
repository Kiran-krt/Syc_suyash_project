package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class SettingsUpdateDateFormatCommand(
    id: String = "",
    tenantId: String = "",
    var dateFormat: String = "dd/MM/yyyy",
) : TenantBaseCommand(id = id, tenantId = tenantId)
