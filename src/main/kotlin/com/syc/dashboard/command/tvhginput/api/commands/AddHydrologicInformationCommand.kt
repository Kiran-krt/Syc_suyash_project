package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AddHydrologicInformationCommand(
    id: String = "",
    var designStormId: String = "",
    var zeroToTenMinuteDuration: String = "",
    var tenToFourtyMinuteDuration: String = "",
    var fourtyToOneHundredFiftyMinuteDuration: String = "",
    var createdBy: String = "",
) : TenantBaseCommand(id = id)
