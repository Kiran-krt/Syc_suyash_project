package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class SettingsTimesheetDelayInHoursUpdatedEvent(
    id: String = "",
    val timesheetDelayInHours: Int = 8,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "SettingsTimesheetDelayInHoursUpdatedEvent"
    }
}
