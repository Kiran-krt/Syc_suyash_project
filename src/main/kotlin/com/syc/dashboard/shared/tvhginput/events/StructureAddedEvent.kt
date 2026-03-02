package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class StructureAddedEvent(
    id: String = "",
    var structureNumber: String = "",
    var structureTypeId: String = "",
    var overflowElevation: String = "",
    var contributionArea: String = "",
    var runoffCoefficient: String = "",
    var timeOfConcentration: String = "",
    var createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "StructureAddedEvent"
    }
}
