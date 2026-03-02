package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class PipeDrawingInformationAllFieldsUpdatedEvent(
    id: String,
    var pipeDrawingInformationId: String = "",
    var pipeInformationId: String = "",
    var pipeMaterialId: String = "",
    var distanceBetweenStructures: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "PipeDrawingInformationAllFieldsUpdatedEvent"
    }
}
