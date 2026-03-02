package com.syc.dashboard.shared.project.notification.events

import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import java.util.*

class ProjectRegisteredEventNotification(
    id: String = "",
    var projectCode: String = "",
    var status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
    var description: String = "",
    var createdBy: String = "",
    val createdOn: Date = Date(),
    var quickBookDescription: String = "",
) : NotificationBaseEvent(id = id) {

    companion object {
        const val EVENT_NAME = "ProjectRegisteredEventNotification"
    }
}
