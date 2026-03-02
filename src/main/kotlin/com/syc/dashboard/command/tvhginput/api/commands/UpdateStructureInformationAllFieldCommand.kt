package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhginput.entity.enums.StructureInformationStatusEnum
import java.util.*

class UpdateStructureInformationAllFieldCommand(
    id: String = "",
    var structureInformationId: String = "",
    var structureId: String = "",
    var structureNumber: String = "",
    var structureTypeId: String = "",
    var overflowElevation: String = "",
    var contributionArea: String = "",
    var runoffCoefficient: String = "",
    var timeOfConcentration: String = "",
    var status: StructureInformationStatusEnum = StructureInformationStatusEnum.ACTIVE,
    var createdBy: String = "",
) : TenantBaseCommand(id = id)
