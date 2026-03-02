package com.syc.dashboard.shared.jobcode.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum

class JobCodeStatusUpdatedEvent(
    id: String,
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "JobCodeStatusUpdatedEvent"
    }
}
