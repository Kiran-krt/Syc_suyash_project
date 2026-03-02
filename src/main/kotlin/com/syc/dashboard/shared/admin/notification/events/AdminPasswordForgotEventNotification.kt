package com.syc.dashboard.shared.admin.notification.events

import com.syc.dashboard.framework.core.events.NotificationBaseEvent

class AdminPasswordForgotEventNotification(
    id: String = "",
    var password: String = "",
    var passwordText: String = "",
    var passwordUpdated: Boolean = false,
    var email: String = "",
) : NotificationBaseEvent(id = id) {

    companion object {
        const val EVENT_NAME = "AdminPasswordForgotEventNotification"
    }
}
