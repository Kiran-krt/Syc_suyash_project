package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.expensereport.api.queries.FindExpenseReportCountForAdminByStatusQuery
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindExpenseReportCountForAdminByStatusQueryHandler(
    private val expenseReportReactiveRepository: ExpenseReportReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindExpenseReportCountForAdminByStatusQuery
        return expenseReportReactiveRepository
            .expenseReportCountByTenantIdAndStatus(
                tenantId = query.tenantId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }
}
