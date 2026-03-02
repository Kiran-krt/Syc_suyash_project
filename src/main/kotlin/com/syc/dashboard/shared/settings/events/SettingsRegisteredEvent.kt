package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum
import java.util.*

class SettingsRegisteredEvent(
    id: String,
    val dateFormat: String = "MM/dd/YYYY",
    val timeZone: String = "EST",
    val timesheetDelayInHours: Int = 8,
    val status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE,
    val createdBy: String = "",
    val createdDate: Date = Date(),
    val mileageRateLabel: String = "",
    val mileageRateDescription: String = "",
    val mileageRateValue: Double = 0.655,
    var yearlyQuarterName: String = "",
    var yearlyQuarterDescription: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "SettingsRegisteredEvent"
    }
}
