package com.syc.dashboard.command.tvhginput.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AddInletControlParameterCommand(
    id: String = "",
    var inletControlParameterId: String = "",
    val cparameter: String = "",
    val yparameter: String = "",
    val kparameter: String = "",
    val mparameter: String = "",
    val equationForm: String = "",
) : TenantBaseCommand(id = id)
