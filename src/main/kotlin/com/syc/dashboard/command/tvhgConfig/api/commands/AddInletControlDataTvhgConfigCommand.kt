package com.syc.dashboard.command.tvhgConfig.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.tvhgConfig.entity.enums.InletControlDataStatusEnum

class AddInletControlDataTvhgConfigCommand(
    id: String = "",
    var inletControlDataId: String = "",
    var inletId: String = "",
    var pathNumber: String = "",
    var inletControlDataName: String = "",
    var cparameter: String = "",
    var yparameter: String = "",
    var kparameter: String = "",
    var mparameter: String = "",
    var equationForm: String = "",
    var status: InletControlDataStatusEnum = InletControlDataStatusEnum.ACTIVE,
) : TenantBaseCommand(id = id)
