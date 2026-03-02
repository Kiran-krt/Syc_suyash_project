package com.syc.dashboard.shared.document.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.entity.enums.DocumentObjectTypeEnum
import com.syc.dashboard.query.document.entity.enums.DocumentStatusEnum
import java.util.*

class DocumentUploadedEvent(
    id: String = "",
    val objectId: String = "",
    val documentEntity: String = "",
    val documentObjectType: DocumentObjectTypeEnum = DocumentObjectTypeEnum.USER,
    val documentName: String = "",
    val extension: String = "",
    val cloudFilePath: String = "",
    val status: DocumentStatusEnum = DocumentStatusEnum.ACTIVE,
    var createdDate: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "DocumentUploadedEvent"
    }
}
