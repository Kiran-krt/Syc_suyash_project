package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.OutletStructureTypeStatusEnum

class UpdateOutletStructureTypeAllFieldsCommand(
    id: String = "",
    var outletStructureTypeId: String = "",
    var outletStructureType: String = "",
    var status: OutletStructureTypeStatusEnum = OutletStructureTypeStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
