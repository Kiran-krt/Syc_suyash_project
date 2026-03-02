package com.syc.dashboard.shared.notification.inapp.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum

class InAppNotificationStatusUpdatedEvent(
    id: String,
    var status: InAppNotificationStatusEnum = InAppNotificationStatusEnum.READ,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "InAppNotificationStatusUpdatedEvent"
    }
}
