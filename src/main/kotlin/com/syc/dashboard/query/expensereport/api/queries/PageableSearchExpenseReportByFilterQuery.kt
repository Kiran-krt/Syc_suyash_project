package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum
import org.springframework.data.domain.Sort

class PageableSearchExpenseReportByFilterQuery(
    val status: List<ExpenseReportStatusEnum> = ExpenseReportStatusEnum.values().toList(),
    val employeeId: String = "",
    val supervisorId: String = "",
    val description: String = "",
    val page: Int = 0,
    val limit: Int = 50,
    val sort: Sort.Direction = Sort.Direction.DESC,
) : TenantBaseQuery()
