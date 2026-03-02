package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.entity.enums.OutfallPhotoStatusEnum

class OutfallPhotoAllFieldsUpdatedEvent(
    id: String,
    var outfallPhotoId: String = "",
    var document: List<DocumentIdDto> = listOf(),
    var status: OutfallPhotoStatusEnum = OutfallPhotoStatusEnum.APPROVE,
    var caption: String = "",
    var order: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "OutfallPhotoAllFieldsUpdatedEvent"
    }
}
