package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class SearchExpenseReportByJobCodeAndPeriodFromQuery(
    var jobCodeId: String = "",
    var periodFrom: String = "",
) : TenantBaseQuery()
