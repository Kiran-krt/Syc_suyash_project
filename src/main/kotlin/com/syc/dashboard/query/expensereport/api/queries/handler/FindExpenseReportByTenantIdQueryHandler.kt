package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.expensereport.api.queries.FindExpenseReportByTenantIdQuery
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportReactiveRepository
import reactor.core.publisher.Flux

class FindExpenseReportByTenantIdQueryHandler(
    private val expenseReportReactiveRepository: ExpenseReportReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindExpenseReportByTenantIdQuery
        return expenseReportReactiveRepository
            .findByTenantId(
                tenantId = query.tenantId,
            )
    }
}
