package com.syc.dashboard.command.vehiclelog.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.vehiclelog.entity.enums.VehicleLogStatusEnum

class VehicleLogUpdateStatusByIdCommand(
    id: String = "",
    tenantId: String = "",
    var status: VehicleLogStatusEnum = VehicleLogStatusEnum.REVIEW,
) : TenantBaseCommand(tenantId = tenantId, id = id)
