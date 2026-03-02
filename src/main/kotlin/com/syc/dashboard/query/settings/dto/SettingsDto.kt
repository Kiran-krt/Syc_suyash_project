package com.syc.dashboard.query.settings.dto

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum

class SettingsDto(
    var id: String = "",
    tenantId: String = "",
    var status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE,
    var createdBy: String = "",
    var systemSettings: SystemSettings = SystemSettings(),
    var mileageRateInfo: MileageRateInfo = MileageRateInfo(),
    var yearlyQuarterInfo: YearlyQuarterInfo = YearlyQuarterInfo(),
) : TenantBaseDto(tenantId = tenantId) {
    data class SystemSettings(
        var dateFormat: String = "MM/dd/YYYY",
        var timeZone: String = "EST",
        var timesheetDelayInHours: Int = 8,
    ) : BaseDto()

    data class MileageRateInfo(
        var mileageRateLabel: String = "",
        var mileageRateDescription: String = "",
        var mileageRateValue: Double = 0.655,
    ) : BaseDto()

    data class YearlyQuarterInfo(
        var yearlyQuarterName: String = "",
        var yearlyQuarterDescription: String = "",
    ) : BaseDto()
}
