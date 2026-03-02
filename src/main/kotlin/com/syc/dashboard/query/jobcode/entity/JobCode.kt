package com.syc.dashboard.query.jobcode.entity

import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_jobcode")
class JobCode(
    val id: String,
    var code: String = "",
    var projectId: String = "",
    var createdBy: String = "",
    var createdByInfo: BaseEntity? = null,
    var createdOn: Date = Date(),
    var costCodeList: MutableList<String> = mutableListOf(),
    var projectCodeList: MutableList<String> = mutableListOf(),
    var status: JobCodeStatusEnum = JobCodeStatusEnum.ACTIVE,
    var watcherList: MutableList<String> = mutableListOf(),
    var watcherListInfo: MutableList<EmployeeDto>? = mutableListOf(),
    var description: String = "",
    var quickBookDescription: String = "",
    var projectIdInfo: BaseEntity? = null,
) : TenantBaseEntity()
