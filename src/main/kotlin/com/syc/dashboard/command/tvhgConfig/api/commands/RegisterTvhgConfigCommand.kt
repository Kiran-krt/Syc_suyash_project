package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import java.util.*

class RegisterTvhgConfigCommand(
    id: String = "",
    tenantId: String = "",
    val createdBy: String = "",
) : TenantBaseCommand(id = id, tenantId = tenantId)
