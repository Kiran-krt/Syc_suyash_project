package com.syc.dashboard.shared.employee.events

import com.syc.dashboard.framework.core.events.TenantBaseEvent

class EmployeeMobileDeviceInfoUpdatedEvent(
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
) : TenantBaseEvent(id = id, version = -1) {
    companion object {
        const val EVENT_NAME = "EmployeeMobileDeviceInfoUpdatedEvent"
    }
}
