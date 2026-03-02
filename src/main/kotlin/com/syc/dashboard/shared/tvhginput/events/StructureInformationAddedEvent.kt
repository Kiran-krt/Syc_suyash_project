package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhginput.entity.enums.StructureInformationStatusEnum
import java.util.*

class StructureInformationAddedEvent(
    id: String = "",
    val structureInformationId: String = "",
    var structureId: String = "",
    var structureNumber: String = "",
    var structureTypeId: String = "",
    var overflowElevation: String = "",
    var contributionArea: String = "",
    var runoffCoefficient: String = "",
    var timeOfConcentration: String = "",
    var status: StructureInformationStatusEnum = StructureInformationStatusEnum.ACTIVE,
    var createdBy: String = "",
    var createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "StructureInformationAddedEvent"
    }
}
