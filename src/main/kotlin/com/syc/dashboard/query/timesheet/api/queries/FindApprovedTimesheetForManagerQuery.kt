package com.syc.dashboard.query.timesheet.api.queries

import com.syc.dashboard.framework.common.utils.DateUtils
import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import org.springframework.data.domain.Sort

class FindApprovedTimesheetForManagerQuery(
    var managerId: String = "",
    var weekStartingYear: Int? = 2000,
    var weekStartingMonth: Int? = 1,
    var weekStartingDay: Int? = 1,
    var weekEndingYear: Int? = DateUtils.getCurrentYear() + 5,
    var weekEndingMonth: Int? = DateUtils.getCurrentMonth() + 1,
    var weekEndingDay: Int? = DateUtils.getCurrentDay() + 1,
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
