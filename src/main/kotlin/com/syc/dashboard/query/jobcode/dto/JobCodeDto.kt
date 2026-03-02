package com.syc.dashboard.query.jobcode.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import com.syc.dashboard.query.project.dto.ProjectDto
import java.util.*

class JobCodeDto(
    var id: String = "",
    tenantId: String = "",
    var code: String = "",
    var createdBy: String = "",
    var createdByInfo: AdminDto? = null,
    var createdOn: Date = Date(),
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var watcherList: MutableList<String> = mutableListOf(),
    var watcherListInfo: MutableList<EmployeeDto>? = mutableListOf(),
    var description: String = "",
    var projectId: String = "",
    var quickBookDescription: String = "",
    var projectIdInfo: ProjectDto? = null,
) : TenantBaseDto(tenantId = tenantId)
