package com.syc.dashboard.command.projectreport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.projectreport.entity.enums.AppendixStatusEnum

class AppendixUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    var appendixId: String = "",
    var status: AppendixStatusEnum = AppendixStatusEnum.ACTIVE,
) : TenantBaseCommand(tenantId = tenantId, id = id)
