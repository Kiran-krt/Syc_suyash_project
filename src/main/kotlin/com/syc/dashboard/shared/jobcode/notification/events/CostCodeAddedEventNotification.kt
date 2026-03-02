package com.syc.dashboard.shared.jobcode.notification.events

import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import java.util.*

class CostCodeAddedEventNotification(
    id: String = "",
    var jobCodeId: String = "",
    var code: String = "",
    var description: String = "",
    var createdBy: String = "",
    var status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
    var createdOn: Date = Date(),
) : NotificationBaseEvent(id = id) {

    companion object {
        const val EVENT_NAME = "CostCodeAddedEventNotification"
    }
}
