package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.PipeTypeStatusEnum

class PipeTypeAllFieldsUpdatedEvent(
    id: String = "",
    var pipeTypeId: String = "",
    var typeId: String = "",
    var description: String = "",
    var status: PipeTypeStatusEnum = PipeTypeStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "PipeTypeAllFieldsUpdatedEvent"
    }
}
