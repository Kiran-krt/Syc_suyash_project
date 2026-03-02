package com.syc.dashboard.command.systemconfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.systemconfig.entity.enums.SystemConfigStatusEnum

class SystemConfigUpdateAllFieldsCommand(
    id: String = "",
    val appName: String = "",
    val logo: MutableList<DocumentIdDto> = mutableListOf(),
    val status: SystemConfigStatusEnum = SystemConfigStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
