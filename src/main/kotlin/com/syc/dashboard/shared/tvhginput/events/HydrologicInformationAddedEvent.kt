package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class HydrologicInformationAddedEvent(
    id: String = "",
    var designStormId: String = "",
    var zeroToTenMinuteDuration: String = "",
    var tenToFourtyMinuteDuration: String = "",
    var fourtyToOneHundredFiftyMinuteDuration: String = "",
    var createdBy: String = "",
    var createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "HydrologicInformationAddedEvent"
    }
}
