package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.expensereport.api.queries.SearchExpenseReportByJobCodeAndPeriodFromQuery
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportRowReactiveRepository
import reactor.core.publisher.Flux

class SearchExpenseReportByJobCodeAndPeriodFromQueryHandler(
    private val expenseReportRowReactiveRepository: ExpenseReportRowReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchExpenseReportByJobCodeAndPeriodFromQuery
        return expenseReportRowReactiveRepository
            .findAllByTenantIdAndJobCodeIdAndPeriodFromAndExpenseReportStatusIn(
                tenantId = query.tenantId,
                jobCodeId = query.jobCodeId,
                periodFrom = query.periodFrom,
            )
    }
}
