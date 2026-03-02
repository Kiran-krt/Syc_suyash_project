package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhginput.dto.DistanceElevationDataDto
import java.util.*

class AddedOutletDrawingInformationEvent(
    id: String,
    var outletStructureTypeId: String = "",
    var lengthOfRipRap: String = "",
    var classOfRipRap: String = "",
    var distanceElevationData: MutableList<DistanceElevationDataDto> = mutableListOf(),
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedOutletDrawingInformationEvent"
    }
}
