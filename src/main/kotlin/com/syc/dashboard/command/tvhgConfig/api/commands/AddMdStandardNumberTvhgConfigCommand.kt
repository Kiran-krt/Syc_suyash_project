package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureClassEnum
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureClassStatusEnum

class AddMdStandardNumberTvhgConfigCommand(
    id: String = "",
    var mdStandardNumberId: String = "",
    var structureClass: StructureClassEnum = StructureClassEnum.INLET,
    val mdStandardNumber: String = "",
    var type: String = "",
    var status: StructureClassStatusEnum = StructureClassStatusEnum.ACTIVE,

) : TenantBaseCommand(id = id)
