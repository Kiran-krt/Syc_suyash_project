package com.syc.dashboard.command.jobcode.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum

class JobCodeUpdateStatusCommand(
    id: String = "",
    tenantId: String = "",
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
) : TenantBaseCommand(tenantId = tenantId, id = id)
