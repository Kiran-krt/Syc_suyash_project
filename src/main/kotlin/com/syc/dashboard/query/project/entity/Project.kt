package com.syc.dashboard.query.project.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.admin.dto.AdminDto
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_project")
class Project(
    @Id
    val id: String,
    var projectCode: String = "",
    var status: ProjectStatusEnum = ProjectStatusEnum.ACTIVE,
    var description: String = "",
    var createdBy: String = "",
    var createdByInfo: AdminDto? = null,
    var createdOn: Date = Date(),
    var quickBookDescription: String = "",
) : TenantBaseEntity()
