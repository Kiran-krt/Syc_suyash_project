package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhginput.entity.enums.ProjectInformationStatusEnum
import java.util.*

class AddedProjectInformationInTvhgInputEvent(
    id: String,
    val title: String = "",
    val numberOfStructures: String = "",
    val numberOfFlowPaths: String = "",
    val tailwaterElevationOutlet: String = "",
    val hydrologicInformation: String = "",
    val drawingInformation: String = "",
    val status: ProjectInformationStatusEnum = ProjectInformationStatusEnum.ACTIVE,
    val choiceOfUnitsId: String = "",
    val createdBy: String = "",
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedProjectInformationInTvhgInputEvent"
    }
}
