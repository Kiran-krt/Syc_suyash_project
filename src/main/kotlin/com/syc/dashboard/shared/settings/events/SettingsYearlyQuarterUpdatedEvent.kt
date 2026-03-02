package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class SettingsYearlyQuarterUpdatedEvent(
    id: String = "",
    var yearlyQuarterName: String = "",
    var yearlyQuarterDescription: String = "",
    var createdDate: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "SettingsYearlyQuarterUpdatedEvent"
    }
}
