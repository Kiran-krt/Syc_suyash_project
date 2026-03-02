package com.syc.dashboard.shared.project.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum

class JobCodeUpdatedByProjectIdEvent(
    id: String = "",
    var projectId: String = "",
    var projectCode: String = "",
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var description: String = "",
    var watcherList: MutableList<String> = mutableListOf(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "JobCodeUpdatedByProjectIdEvent"
    }
}
