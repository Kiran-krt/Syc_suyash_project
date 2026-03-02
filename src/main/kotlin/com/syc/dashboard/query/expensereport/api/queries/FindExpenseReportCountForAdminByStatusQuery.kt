package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum

class FindExpenseReportCountForAdminByStatusQuery(
    var statusList: List<ExpenseReportStatusEnum>,
) : TenantBaseQuery()
