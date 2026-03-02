package com.syc.dashboard.command.jobcode.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum

class UpdateJobCodeCommand(
    id: String = "",
    tenantId: String = "",
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var watcherList: MutableList<String> = mutableListOf(),
    var description: String = "",
    var quickBookDescription: String = "",
    var projectId: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
