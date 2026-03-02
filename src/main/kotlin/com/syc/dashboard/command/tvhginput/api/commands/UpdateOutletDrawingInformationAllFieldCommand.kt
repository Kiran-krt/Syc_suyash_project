package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class UpdateOutletDrawingInformationAllFieldCommand(
    id: String = "",
    val fieldName: String = "",
    val fieldValue: Any? = null,
) : TenantBaseCommand(id = id)
