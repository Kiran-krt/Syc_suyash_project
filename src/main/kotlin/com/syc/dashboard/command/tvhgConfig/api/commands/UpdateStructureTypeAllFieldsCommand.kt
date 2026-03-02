package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureTypeStatusEnum

class UpdateStructureTypeAllFieldsCommand(
    id: String = "",
    var structureTypeId: String = "",
    var typeId: String = "",
    var structureTypeName: String = "",
    var status: StructureTypeStatusEnum = StructureTypeStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
