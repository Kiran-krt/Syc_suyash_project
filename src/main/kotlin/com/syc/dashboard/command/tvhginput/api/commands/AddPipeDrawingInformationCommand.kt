package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AddPipeDrawingInformationCommand(
    id: String = "",
    var pipeDrawingInformationId: String = "",
    var pipeInformationId: String = "",
    var pipeMaterialId: String = "",
    var distanceBetweenStructures: String = "",
) : TenantBaseCommand(id = id)
