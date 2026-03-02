package com.syc.dashboard.query.employee.api.queries

import com.syc.dashboard.framework.common.security.dto.enums.UserRole
import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.employee.entity.enums.EmployeeStatusEnum

class FindEmployeeCountByFilterQuery(
    var roleList: List<UserRole>,
    var statusList: List<EmployeeStatusEnum>,
) : TenantBaseQuery()
