package com.syc.dashboard.command.projectreport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.projectreport.entity.enums.AppendixStatusEnum

class AddAppendixCommand(
    id: String = "",
    tenantId: String = "",
    var appendixId: String = "",
    var name: String = "",
    var order: String = "",
    var status: AppendixStatusEnum = AppendixStatusEnum.ACTIVE,
    var createdBy: String = "",
) : TenantBaseCommand(id = id, tenantId = tenantId)
