package com.syc.dashboard.framework.core.events

abstract class NotificationBaseEvent(
    id: String,
    var sendToInApp: Boolean = false,
    var sendToEmail: Boolean = false,
    var sendToMobilePush: Boolean = false,
    var sendToAdminInApp: Boolean = false,
    var sendToAdminEmail: Boolean = false,
    var sendToAdminMobilePush: Boolean = false,
) : TenantBaseEvent(id = id, version = -1)
