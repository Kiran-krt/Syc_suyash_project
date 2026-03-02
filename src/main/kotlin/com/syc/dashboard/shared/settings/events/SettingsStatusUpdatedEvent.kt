package com.syc.dashboard.shared.settings.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum

class SettingsStatusUpdatedEvent(
    id: String = "",
    val status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "SettingsStatusUpdatedEvent"
    }
}
