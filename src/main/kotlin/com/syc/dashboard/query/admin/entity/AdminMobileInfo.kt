package com.syc.dashboard.query.admin.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_admin_mobile_info")
class AdminMobileInfo(
    @Id
    val id: String,
    val deviceUniqueId: String,
    val firebasePushToken: String,
    val osType: String,
    val brand: String,
    val country: String,
    val deviceId: String,
    val emulator: Boolean,
    val systemVersion: String,
    val timeZone: String,
    val creationDate: Date = Date(),
) : TenantBaseEntity()
