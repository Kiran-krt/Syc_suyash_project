package com.syc.dashboard.shared.jobcode.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import java.util.*

class CostCodeAddedEvent(
    id: String = "",
    var jobCodeId: String = "",
    var code: String = "",
    var description: String = "",
    var createdBy: String = "",
    var status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "CostCodeAddedEvent"
    }
}
