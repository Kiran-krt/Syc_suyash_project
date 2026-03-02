package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class StructureDrawingDataAllFieldsUpdatedEvent(
    id: String = "",
    var structureDrawingDataId: String = "",
    var structureInformationId: String = "",
    var existingOrProposedIndex: String = "",
    var mdshaStandardNumber: String = "",
    var typeOfStructure: String = "",
    var structureClass: String = "",
    var station: String = "",
    var offset: String = "",
    var createdBy: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "StructureDrawingDataAllFieldsUpdatedEvent"
    }
}
