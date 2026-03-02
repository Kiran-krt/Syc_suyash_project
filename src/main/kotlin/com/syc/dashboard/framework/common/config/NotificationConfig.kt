package com.syc.dashboard.framework.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.notification")
data class NotificationConfig(
    val events: Map<String, NotificationEventInfo>,
    val emailConfig: EmailConfig,
) {

    data class NotificationEventInfo(
        val eventClass: String,
        val sendToInApp: Boolean = false,
        val sendToEmail: Boolean = false,
        val sendToMobilePush: Boolean = false,
        val sendToAdminInApp: Boolean = false,
        val sendToAdminEmail: Boolean = false,
        val sendToAdminMobilePush: Boolean = false,
    )

    data class EmailConfig(
        val api: String,
    )
}
