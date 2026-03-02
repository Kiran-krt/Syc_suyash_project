package com.syc.dashboard.query.admin.dto

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum
import java.util.*

class AdminDto(
    var id: String = "",
    tenantId: String = "",
    var title: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var gender: String = "",
    var dateOfBirth: String = "",
    var employeeNumber: String = "",
    var email: String = "",
    var passwordUpdated: Boolean = false,
    var status: AdminStatusEnum = AdminStatusEnum.ACTIVE,
    var role: UserRole = UserRole.ADMIN,
    var creationDate: Date = Date(),
) : TenantBaseDto(tenantId = tenantId)
