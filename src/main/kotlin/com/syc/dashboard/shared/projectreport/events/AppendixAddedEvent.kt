package com.syc.dashboard.shared.projectreport.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.projectreport.entity.enums.AppendixStatusEnum
import java.util.*

class AppendixAddedEvent(
    id: String,
    var projectReportId: String = "",
    var name: String = "",
    var order: String = "",
    var status: AppendixStatusEnum = AppendixStatusEnum.ACTIVE,
    var createdBy: String = "",
    val createdDate: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AppendixAddedEvent"
    }
}
