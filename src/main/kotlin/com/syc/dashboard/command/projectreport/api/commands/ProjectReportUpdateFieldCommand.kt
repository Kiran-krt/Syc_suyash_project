package com.syc.dashboard.command.projectreport.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class ProjectReportUpdateFieldCommand(
    id: String = "",
    val fieldName: String = "",
    val fieldValue: Any? = null,
) : TenantBaseCommand(id = id)
