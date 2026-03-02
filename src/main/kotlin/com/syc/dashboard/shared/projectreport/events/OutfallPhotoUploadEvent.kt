package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.entity.enums.OutfallPhotoStatusEnum

class OutfallPhotoUploadEvent(
    id: String,
    var projectReportId: String = "",
    var document: List<DocumentIdDto> = listOf(),
    var status: OutfallPhotoStatusEnum = OutfallPhotoStatusEnum.APPROVE,
    var caption: String = "",
    var order: String = "",
    var uploadedBy: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "OutfallPhotoUploadEvent"
    }
}
