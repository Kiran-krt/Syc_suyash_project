package com.syc.dashboard.command.project.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import java.util.*

class RegisterProjectCommand(
    id: String = "",
    var projectCode: String = "",
    var status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
    var description: String = "",
    var createdBy: String = "",
    var quickBookDescription: String = "",
) : TenantBaseCommand(id = id)
