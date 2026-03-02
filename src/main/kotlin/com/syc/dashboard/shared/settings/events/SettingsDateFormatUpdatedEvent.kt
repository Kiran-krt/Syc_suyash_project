package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class SettingsDateFormatUpdatedEvent(
    id: String = "",
    val dateFormat: String = "dd/MM/yyyy",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "SettingsDateFormatUpdatedEvent"
    }
}
