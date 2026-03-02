package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureTypeStatusEnum

class AddStructureTypeInTvhgConfigCommand(
    id: String = "",
    var structureTypeId: String = "",
    var typeId: String = "",
    val structureTypeName: String = "",
    val status: StructureTypeStatusEnum = StructureTypeStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
