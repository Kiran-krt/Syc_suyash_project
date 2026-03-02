package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhginput.entity.enums.PipeInformationStatusEnum
import java.util.*

class AddedPipeInformationTvhgInputEvent(
    id: String,
    var pipeInformationId: String = "",
    var pipeId: String = "",
    var pipeNumber: String = "",
    var downstreamStructureNumber: String = "",
    var upstreamStructureNumber: String = "",
    var downstreamInvertElevation: String = "",
    var upstreamInvertElevation: String = "",
    var pipeTypeId: String = "",
    var roughnessCoefficient: String = "",
    var pipeLength: String = "",
    var intersectionAngle: String = "",
    var discharge: String = "",
    var status: PipeInformationStatusEnum = PipeInformationStatusEnum.ACTIVE,
    var createdBy: String = "",
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedPipeInformationTvhgInputEvent"
    }
}
