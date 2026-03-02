package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class ExportExpenseReportToExcelByIdQuery(
    val expenseReportId: String = "",
) : TenantBaseQuery()
