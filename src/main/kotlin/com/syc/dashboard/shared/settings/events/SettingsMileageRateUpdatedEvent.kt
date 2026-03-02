package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class SettingsMileageRateUpdatedEvent(
    id: String = "",
    var mileageRateLabel: String = "",
    var mileageRateDescription: String = "",
    var mileageRateValue: Double = 0.655,
    var createdDate: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "SettingsMileageRateUpdatedEvent"
    }
}
