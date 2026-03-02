package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.DesignStormStatusEnum

class AddDesignStormTvhgConfigCommand(
    id: String = "",
    var designStormId: String = "",
    val designStormName: String = "",
    val status: DesignStormStatusEnum = DesignStormStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
