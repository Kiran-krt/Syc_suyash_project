package com.syc.dashboard.command.admin.api.commands

import com.syc.dashboard.framework.core.commands.TenantBaseCommand

class AdminUpdateMobileDeviceInfoCommand(
    id: String = "",
    tenantId: String = "",
    val deviceUniqueId: String,
    val firebasePushToken: String,
    val osType: String,
    val brand: String,
    val country: String,
    val deviceId: String,
    val emulator: Boolean,
    val systemVersion: String,
    val timeZone: String,
) : TenantBaseCommand(tenantId = tenantId, id = id)
