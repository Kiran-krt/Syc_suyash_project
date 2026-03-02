package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.PipeMaterialStatusEnum

class UpdatePipeMaterialAllFieldsCommand(
    id: String = "",
    var pipeMaterialId: String = "",
    var pipeMaterialType: String = "",
    var typeId: String = "",
    var status: PipeMaterialStatusEnum = PipeMaterialStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
