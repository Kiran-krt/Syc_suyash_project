package com.syc.dashboard.command.project.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum

class ProjectUpdateAllFieldsCommand(
    id: String = "",
    val projectCode: String = "",
    val status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
    val description: String = "",
    var quickBookDescription: String = "",
) : TenantBaseCommand(id = id)
