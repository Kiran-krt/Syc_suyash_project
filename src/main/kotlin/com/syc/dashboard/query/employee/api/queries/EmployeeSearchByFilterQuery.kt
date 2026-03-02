package com.syc.dashboard.query.employee.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum

class EmployeeSearchByFilterQuery(
    var status: List<EmployeeStatusEnum> = listOf(EmployeeStatusEnum.ACTIVE),
) : TenantBaseQuery()
