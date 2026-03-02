package com.syc.dashboard.shared.admin.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent
import java.util.*

class AdminMobileDeviceInfoUpdatedEvent(
    id: String,
    val deviceUniqueId: String = "",
    val firebasePushToken: String = "",
    val osType: String = "",
    val brand: String = "",
    val country: String = "",
    val deviceId: String = "",
    val emulator: Boolean = true,
    val systemVersion: String = "",
    val timeZone: String = "",
    val createdDate: Date = Date(),
) : TenantBaseEvent(id = id, version = -1) {
    companion object {
        const val EVENT_NAME = "AdminMobileDeviceInfoUpdatedEvent"
    }
}
