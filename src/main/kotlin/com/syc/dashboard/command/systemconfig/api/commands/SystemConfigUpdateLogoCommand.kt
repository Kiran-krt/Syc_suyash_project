package com.syc.dashboard.command.systemconfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.document.dto.DocumentIdDto

class SystemConfigUpdateLogoCommand(
    id: String = "",
    val logo: MutableList<DocumentIdDto> = mutableListOf(),
) : TenantBaseCommand(id = id)
