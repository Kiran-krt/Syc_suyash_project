package com.syc.dashboard.query.project.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import java.util.*

class ProjectDto(
    var id: String = "",
    tenantId: String = "",
    var projectCode: String = "",
    var createdByInfo: AdminDto? = null,
    var status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
    var description: String = "",
    var quickBookDescription: String = "",
    var createdBy: String = "",
    var createdOn: Date = Date(),
) : TenantBaseDto(tenantId = tenantId)
