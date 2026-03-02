package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class AdminRegisteredEvent(
    id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val dateOfBirth: String,
    val employeeNumber: String,
    val email: String,
    val password: String = "welcome1",
    val passwordUpdated: Boolean = false,
    val createdDate: Date,
) : TenantBaseEvent(id = id, version = -1) {

    companion object {
        const val EVENT_NAME = "AdminRegisteredEvent"
    }
}
