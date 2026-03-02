package com.syc.dashboard.shared.tvhginput.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class AddedInletControlParameterEvent(
    id: String,
    var inletControlParameterId: String = "",
    var cparameter: String = "",
    var yparameter: String = "",
    var kparameter: String = "",
    var mparameter: String = "",
    var equationForm: String = "",
    val createdOn: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AddedInletControlParameterEvent"
    }
}
