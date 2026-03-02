package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureClassEnum
import com.syc.dashboard.query.tvhgConfig.entity.enums.StructureClassStatusEnum

class MdStandardNumberAllFieldsUpdatedEvent(
    id: String = "",
    var mdStandardNumberId: String = "",
    var structureClass: StructureClassEnum = StructureClassEnum.INLET,
    val mdStandardNumber: String = "",
    var type: String = "",
    var status: StructureClassStatusEnum = StructureClassStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "MdStandardNumberAllFieldsUpdatedEvent"
    }
}
