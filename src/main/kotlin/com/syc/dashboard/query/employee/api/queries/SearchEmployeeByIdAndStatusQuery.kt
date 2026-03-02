package com.syc.dashboard.query.employee.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum

class SearchEmployeeByIdAndStatusQuery(
    var managerId: String = "",
    var status: EmployeeStatusEnum = EmployeeStatusEnum.ACTIVE,
) : TenantBaseQuery()
