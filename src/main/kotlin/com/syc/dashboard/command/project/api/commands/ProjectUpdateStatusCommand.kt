package com.syc.dashboard.command.project.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum

class ProjectUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    var status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
) : TenantBaseCommand(tenantId = tenantId, id = id)
