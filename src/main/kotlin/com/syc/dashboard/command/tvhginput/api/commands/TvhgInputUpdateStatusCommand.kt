package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhginput.entity.enums.TvhgInputStatusEnum

class TvhgInputUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    var status: TvhgInputStatusEnum = TvhgInputStatusEnum.ACTIVE,
) : TenantBaseCommand(tenantId = tenantId, id = id)
