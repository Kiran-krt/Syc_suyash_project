package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureTypeStatusEnum

class StructureTypeAllFieldsUpdatedEvent(
    id: String = "",
    var structureTypeId: String = "",
    var typeId: String = "",
    var structureTypeName: String = "",
    var status: StructureTypeStatusEnum = StructureTypeStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "StructureTypeAllFieldsUpdatedEvent"
    }
}
