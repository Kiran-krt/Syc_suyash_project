package com.syc.dashboard.shared.project.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import java.util.*

class ProjectRegisteredEvent(
    id: String,
    var projectCode: String = "",
    var status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
    var description: String = "",
    var createdBy: String = "",
    val createdOn: Date = Date(),
    var quickBookDescription: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ProjectRegisteredEvent"
    }
}
