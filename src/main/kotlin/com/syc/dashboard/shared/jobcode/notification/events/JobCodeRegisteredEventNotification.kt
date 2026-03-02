package com.syc.dashboard.shared.jobcode.notification.events

import com.syc.dashboard.framework.core.events.NotificationBaseEvent
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import java.util.*

class JobCodeRegisteredEventNotification(
    id: String = "",
    var code: String = "",
    var projectId: String = "",
    var createdBy: String = "",
    val createdDate: Date = Date(),
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var watcherList: MutableList<String> = mutableListOf(),
    var description: String = "",
    var quickBookDescription: String = "",
) : NotificationBaseEvent(id = id) {

    companion object {
        const val EVENT_NAME = "JobCodeRegisteredEventNotification"
    }
}
