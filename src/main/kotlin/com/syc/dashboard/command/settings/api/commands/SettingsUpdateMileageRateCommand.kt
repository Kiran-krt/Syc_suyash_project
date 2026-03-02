package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class SettingsUpdateMileageRateCommand(
    id: String = "",
    tenantId: String = "",
    var mileageRateLabel: String = "",
    var mileageRateDescription: String = "",
    var mileageRateValue: Double = 0.655,
) : TenantBaseCommand(id = id, tenantId = tenantId)
