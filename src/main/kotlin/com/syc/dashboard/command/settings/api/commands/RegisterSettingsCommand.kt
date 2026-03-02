package com.syc.dashboard.command.settings.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum

class RegisterSettingsCommand(
    id: String = "",
    val dateFormat: String = "MM/dd/YYYY",
    val timeZone: String = "EST",
    val timesheetDelayInHours: Int = 8,
    val status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE,
    val createdBy: String = "",
    val mileageRateLabel: String = "",
    val mileageRateDescription: String = "",
    val mileageRateValue: Double = 0.655,
    val yearlyQuarterName: String = "",
    val yearlyQuarterDescription: String = "",
) : TenantBaseCommand(id = id)
