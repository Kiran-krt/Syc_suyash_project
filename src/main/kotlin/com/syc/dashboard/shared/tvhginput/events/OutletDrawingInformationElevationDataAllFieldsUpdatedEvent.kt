package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class OutletDrawingInformationElevationDataAllFieldsUpdatedEvent(
    id: String,
    var distanceElevationId: String = "",
    var distanceFromOutlet: String = "",
    var elevation: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "OutletDrawingInformationElevationDataAllFieldsUpdatedEvent"
    }
}
