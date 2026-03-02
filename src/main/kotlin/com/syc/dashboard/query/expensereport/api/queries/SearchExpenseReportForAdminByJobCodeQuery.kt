package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.core.queries.TenantBaseQuery

class SearchExpenseReportForAdminByJobCodeQuery(
    var jobCodeId: String = "",
    var periodFrom: String = "",
) : TenantBaseQuery()
