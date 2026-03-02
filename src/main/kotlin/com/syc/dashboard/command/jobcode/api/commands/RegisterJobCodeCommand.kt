package com.syc.dashboard.command.jobcode.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum

class RegisterJobCodeCommand(
    id: String = "",
    val code: String = "",
    val projectId: String = "",
    val createdBy: String = "",
    val status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var watcherList: MutableList<String> = mutableListOf(),
    val description: String = "",
    val quickBookDescription: String = "",
) : TenantBaseCommand(id = id)
