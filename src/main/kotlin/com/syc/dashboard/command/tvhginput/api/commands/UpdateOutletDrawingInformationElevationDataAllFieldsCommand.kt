package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class UpdateOutletDrawingInformationElevationDataAllFieldsCommand(
    id: String = "",
    var distanceElevationId: String = "",
    var distanceFromOutlet: String = "",
    var elevation: String = "",
) : TenantBaseCommand(id = id)
