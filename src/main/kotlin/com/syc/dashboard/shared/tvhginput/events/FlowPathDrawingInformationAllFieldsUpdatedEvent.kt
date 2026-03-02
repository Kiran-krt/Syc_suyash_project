package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class FlowPathDrawingInformationAllFieldsUpdatedEvent(
    id: String,
    var flowPathDrawingInformationId: String = "",
    var inletControlDataId: String = "",
    var pathTitle: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "FlowPathDrawingInformationAllFieldsUpdatedEvent"
    }
}
