package com.syc.dashboard.shared.jobcode.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import java.util.*

class JobCodeRegisteredEvent(
    id: String,
    val code: String = "",
    val projectId: String = "",
    val createdBy: String = "",
    val createdDate: Date = Date(),
    val status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var watcherList: MutableList<String> = mutableListOf(),
    val description: String = "",
    val quickBookDescription: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "JobCodeRegisteredEvent"
    }
}
