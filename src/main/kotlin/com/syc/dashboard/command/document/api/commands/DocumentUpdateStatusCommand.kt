package com.syc.dashboard.command.document.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.document.entity.enums.DocumentStatusEnum

class DocumentUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    val status: DocumentStatusEnum = DocumentStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id, tenantId = tenantId)
