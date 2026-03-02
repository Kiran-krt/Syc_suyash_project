package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class SettingsTimeZoneUpdatedEvent(
    id: String = "",
    val timeZone: String = "EST",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "SettingsTimeZoneUpdatedEvent"
    }
}
