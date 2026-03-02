package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureTypeStatusEnum
import java.util.*

class AddedStructureTypeInTvhgConfigEvent(
    id: String,
    var structureTypeId: String = "",
    var typeId: String = "",
    val structureTypeName: String = "",
    val status: StructureTypeStatusEnum = StructureTypeStatusEnum.ACTIVE,
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedStructureTypeInTvhgConfigEvent"
    }
}
