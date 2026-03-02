package com.syc.dashboard.query.settings.entity

import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_settings")
class Settings(
    @Id
    var id: String,
    var systemSettings: SystemSettings,
    var status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE,
    var createdBy: String,
    var createdOn: Date = Date(),
    var mileageRateInfo: MileageRateInfo,
    var yearlyQuarterInfo: YearlyQuarterInfo? = null,
) : TenantBaseEntity() {
    data class SystemSettings(
        var dateFormat: String = "MM/dd/YYYY",
        var timeZone: String = "EST",
        var timesheetDelayInHours: Int = 8,
    ) : BaseEntity()
    data class MileageRateInfo(
        var mileageRateLabel: String = "",
        var mileageRateDescription: String = "",
        var mileageRateValue: Double = 0.655,
    ) : BaseEntity()
    data class YearlyQuarterInfo(
        var yearlyQuarterName: String? = "",
        var yearlyQuarterDescription: String? = "",
    ) : BaseEntity()
}
