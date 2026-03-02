package com.syc.dashboard.shared.jobcode.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum

class JobCodeUpdatedEvent(
    id: String,
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var watcherList: MutableList<String> = mutableListOf(),
    var description: String = "",
    var projectId: String = "",
    var quickBookDescription: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "JobCodeUpdatedEvent"
    }
}
