package com.syc.dashboard.query.admin.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum

class SearchAllUsersByFilterQuery(
    val name: String = "",
    var status: List<EmployeeStatusEnum> = listOf(EmployeeStatusEnum.ACTIVE),
) : TenantBaseQuery()
