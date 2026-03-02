package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.PipeTypeStatusEnum
import java.util.*

class AddedPipeTypeTvhgConfigEvent(
    id: String,
    var pipeTypeId: String = "",
    var typeId: String = "",
    val description: String = "",
    val status: PipeTypeStatusEnum = PipeTypeStatusEnum.ACTIVE,
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedPipeTypeTvhgConfigEvent"
    }
}
