package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhginput.entity.enums.PipeInformationStatusEnum

class AddPipeInformationCommand(
    id: String = "",
    var pipeInformationId: String = "",
    var pipeId: String = "",
    var pipeNumber: String = "",
    var downstreamStructureNumber: String = "",
    var upstreamStructureNumber: String = "",
    var downstreamInvertElevation: String = "",
    var upstreamInvertElevation: String = "",
    var pipeTypeId: String = "",
    var roughnessCoefficient: String = "",
    var pipeLength: String = "",
    var intersectionAngle: String = "",
    var discharge: String = "",
    var status: PipeInformationStatusEnum = PipeInformationStatusEnum.ACTIVE,
    var createdBy: String = "",
) : TenantBaseCommand(id = id)
