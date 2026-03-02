package com.syc.dashboard.query.employee.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "q_employee_mobile_info")
class EmployeeMobileInfo(
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
) : TenantBaseEntity()
