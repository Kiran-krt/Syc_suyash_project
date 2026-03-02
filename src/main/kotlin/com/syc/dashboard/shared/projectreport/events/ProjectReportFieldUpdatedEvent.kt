package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class ProjectReportFieldUpdatedEvent(
    id: String,
    var fieldName: String = "",
    var fieldValue: Any? = null,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "ProjectReportFieldUpdatedEvent"
    }
}
