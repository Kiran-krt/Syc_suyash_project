package com.syc.dashboard.command.project.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum

class UpdateJobCodeByProjectIdCommand(
    id: String = "",
    tenantId: String = "",
    var projectId: String = "",
    var projectCode: String = "",
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var description: String = "",
    var watcherList: MutableList<String> = mutableListOf(),
) : TenantBaseCommand(tenantId = tenantId, id = id)
