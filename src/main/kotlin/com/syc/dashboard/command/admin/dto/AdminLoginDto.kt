package com.syc.dashboard.command.admin.dto

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.TenantBaseDto

class AdminLoginDto(
    tenantId: String = "",
    val email: String,
    val password: String,
    val role: UserRole = UserRole.ADMIN,
) : TenantBaseDto(tenantId = tenantId)
