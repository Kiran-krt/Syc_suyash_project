package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhginput.dto.DistanceElevationDataDto

class AddOutletDrawingInformationCommand(
    id: String = "",
    var outletStructureTypeId: String = "",
    var lengthOfRipRap: String = "",
    var classOfRipRap: String = "",
    var distanceElevationData: MutableList<DistanceElevationDataDto> = mutableListOf(),
) : TenantBaseCommand(id = id)
