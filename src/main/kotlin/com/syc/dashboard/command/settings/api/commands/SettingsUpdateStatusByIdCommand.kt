package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum

class SettingsUpdateStatusByIdCommand(
    id: String = "",
    tenantId: String = "",
    var status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id, tenantId = tenantId)
