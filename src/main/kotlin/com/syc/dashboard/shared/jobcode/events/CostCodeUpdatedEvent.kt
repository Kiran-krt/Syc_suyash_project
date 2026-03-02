package com.syc.dashboard.shared.jobcode.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum

class CostCodeUpdatedEvent(
    id: String = "",
    var jobCodeId: String = "",
    var description: String = "",
    var status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "CostCodeUpdatedEvent"
    }
}
