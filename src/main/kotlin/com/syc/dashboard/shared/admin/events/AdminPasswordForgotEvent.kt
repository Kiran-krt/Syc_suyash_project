package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class AdminPasswordForgotEvent(
    id: String,
    val password: String = "",
    val passwordText: String = "",
    val passwordUpdated: Boolean = false,
    val email: String = "",
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AdminPasswordForgotEvent"
    }
}
