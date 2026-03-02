package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class UpdateFlowPathDrawingInformationAllFieldsCommand(
    id: String = "",
    var flowPathDrawingInformationId: String = "",
    var inletControlDataId: String = "",
    var pathTitle: String = "",
) : TenantBaseCommand(id = id)
