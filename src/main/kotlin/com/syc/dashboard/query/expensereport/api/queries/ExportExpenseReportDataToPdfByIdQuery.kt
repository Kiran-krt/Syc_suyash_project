package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class ExportExpenseReportDataToPdfByIdQuery(
    val expenseReportId: String = "",
) : TenantBaseQuery()
