package com.syc.dashboard.query.employee.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum
import org.springframework.data.domain.Sort

class SearchEmployeeByStatusQuery(
    var status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.ASC,
) : TenantBaseQuery()
