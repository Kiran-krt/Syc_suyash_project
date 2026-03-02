package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum

class DeleteSettingsCommand(
    id: String = "",
    tenantId: String = "",
    val status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE,
) : TenantBaseCommand(tenantId = tenantId, id = id)
