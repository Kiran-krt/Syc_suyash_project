package com.syc.dashboard.shared.systemconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.dto.DocumentIdDto

class SystemConfigLogoUpdatedEvent(
    id: String,
    val logo: MutableList<DocumentIdDto> = mutableListOf(),
) : TenantBaseEvent(
    id = id,
    version = -1,
) {

    companion object {
        const val EVENT_NAME = "SystemConfigLogoUpdatedEvent"
    }
}
