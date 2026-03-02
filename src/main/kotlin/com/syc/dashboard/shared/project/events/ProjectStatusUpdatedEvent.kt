package com.syc.dashboard.shared.project.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum

class ProjectStatusUpdatedEvent(
    id: String,
    var status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ProjectStatusUpdatedEvent"
    }
}
