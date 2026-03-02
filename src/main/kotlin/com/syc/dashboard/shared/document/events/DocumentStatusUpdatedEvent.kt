package com.syc.dashboard.shared.document.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.entity.enums.DocumentStatusEnum

class DocumentStatusUpdatedEvent(
    id: String = "",
    val status: DocumentStatusEnum = DocumentStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "DocumentStatusUpdatedEvent"
    }
}
