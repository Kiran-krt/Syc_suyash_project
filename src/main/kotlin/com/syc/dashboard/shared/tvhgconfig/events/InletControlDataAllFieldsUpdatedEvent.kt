package com.syc.dashboard.shared.tvhgconfig.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.query.tvhgConfig.entity.enums.InletControlDataStatusEnum

class InletControlDataAllFieldsUpdatedEvent(
    id: String = "",
    var inletControlDataId: String = "",
    var inletId: String = "",
    var pathNumber: String = "",
    var inletControlDataName: String = "",
    var cparameter: String = "",
    var yparameter: String = "",
    var kparameter: String = "",
    var mparameter: String = "",
    var equationForm: String = "",
    var status: InletControlDataStatusEnum = InletControlDataStatusEnum.ACTIVE,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "InletControlDataAllFieldsUpdatedEvent"
    }
}
