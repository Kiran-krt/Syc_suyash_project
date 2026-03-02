package com.syc.dashboard.shared.systemconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.systemconfig.entity.enums.SystemConfigStatusEnum

class SystemConfigAllFieldsUpdatedEvent(
    id: String,
    val appName: String = "",
    val logo: MutableList<DocumentIdDto> = mutableListOf(),
    val status: SystemConfigStatusEnum = SystemConfigStatusEnum.ACTIVE,
) : TenantBaseEvent(
    id = id,
    version = -1,
) {

    companion object {
        const val EVENT_NAME = "SystemConfigAllFieldsUpdatedEvent"
    }
}
