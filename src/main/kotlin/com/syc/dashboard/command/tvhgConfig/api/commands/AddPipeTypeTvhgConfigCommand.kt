package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.PipeTypeStatusEnum

class AddPipeTypeTvhgConfigCommand(
    id: String = "",
    var pipeTypeId: String = "",
    var typeId: String = "",
    val description: String = "",
    val status: PipeTypeStatusEnum = PipeTypeStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
