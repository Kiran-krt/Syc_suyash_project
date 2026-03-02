package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.UnitStatusEnum

class AddUnitsInTvhgConfigCommand(
    id: String = "",
    var unitId: String = "",
    val unitName: String = "",
    val status: UnitStatusEnum = UnitStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
