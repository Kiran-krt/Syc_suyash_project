package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.projectreport.entity.enums.ProjectReportStatusEnum

class ProjectReportStatusUpdatedEvent(
    id: String,
    var status: ProjectReportStatusEnum = ProjectReportStatusEnum.IN_PROGRESS,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ProjectReportStatusUpdatedEvent"
    }
}
