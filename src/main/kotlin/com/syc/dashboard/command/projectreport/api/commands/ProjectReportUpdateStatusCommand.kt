package com.syc.dashboard.command.projectreport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum

class ProjectReportUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    var status: ProjectReportStatusEnum = ProjectReportStatusEnum.IN_PROGRESS,
) : TenantBaseCommand(tenantId = tenantId, id = id)
