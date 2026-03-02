package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.expensereport.api.queries.FindExpenseReportCountForSupervisorByStatusQuery
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindExpenseReportCountForSupervisorByStatusQueryHandler(
    private val expenseReportReactiveRepository: ExpenseReportReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindExpenseReportCountForSupervisorByStatusQuery
        return expenseReportReactiveRepository
            .expenseReportCountByTenantIdAndSupervisorIdAndStatus(
                tenantId = query.tenantId,
                supervisorId = query.supervisorId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }
}
