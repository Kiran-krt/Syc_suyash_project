package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class FindExpenseReportByIdQuery(
    val id: String = "",
) : TenantBaseQuery()
