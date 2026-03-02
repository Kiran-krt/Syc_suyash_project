package com.syc.dashboard.query.projectreport.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.projectreport.entity.enums.AppendixStatusEnum
import java.util.*

class AppendixDto(
    var id: String = "",
    tenantId: String = "",
    var projectReportId: String = "",
    var name: String = "",
    var order: String = "",
    var content: String = "",
    var status: AppendixStatusEnum = AppendixStatusEnum.ACTIVE,
    var createdBy: String = "",
    var createdDate: Date = Date(),
    var createdByInfo: AdminDto? = null,
) : TenantBaseDto(tenantId = tenantId)
