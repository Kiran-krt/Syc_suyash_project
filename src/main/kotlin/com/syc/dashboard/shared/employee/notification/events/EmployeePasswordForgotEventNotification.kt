package com.syc.dashboard.shared.employee.notification.events

import com.syc.dashboard.framework.core.events.NotificationBaseEvent

class EmployeePasswordForgotEventNotification(
    id: String = "",
    var password: String = "",
    var passwordText: String = "",
    var passwordUpdated: Boolean = false,
    var email: String = "",
) : NotificationBaseEvent(id = id) {

    companion object {
        const val EVENT_NAME = "EmployeePasswordForgotEventNotification"
    }
}
