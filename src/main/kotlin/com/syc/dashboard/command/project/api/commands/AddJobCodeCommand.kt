package com.syc.dashboard.command.project.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import java.util.*

class AddJobCodeCommand(
    id: String = "",
    tenantId: String = "",
    var projectId: String = "",
    var code: String = "",
    var createdBy: String = "",
    var createdOn: Date = Date(),
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var watcherList: MutableList<String> = mutableListOf(),
    var description: String = "",
) : TenantBaseCommand(tenantId = tenantId, id = id)
