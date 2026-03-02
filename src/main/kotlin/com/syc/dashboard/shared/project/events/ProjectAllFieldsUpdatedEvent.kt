package com.syc.dashboard.shared.project.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum

class ProjectAllFieldsUpdatedEvent(
    id: String,
    val projectCode: String = "",
    val status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
    val description: String = "",
    val quickBookDescription: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ProjectAllFieldsUpdatedEvent"
    }
}
