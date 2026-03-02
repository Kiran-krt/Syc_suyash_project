package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.projectreport.entity.enums.OutfallPhotoStatusEnum

class OutfallPhotoStatusUpdatedEvent(
    id: String,
    var outfallPhotoId: String = "",
    var status: OutfallPhotoStatusEnum = OutfallPhotoStatusEnum.APPROVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "OutfallPhotoStatusUpdatedEvent"
    }
}
