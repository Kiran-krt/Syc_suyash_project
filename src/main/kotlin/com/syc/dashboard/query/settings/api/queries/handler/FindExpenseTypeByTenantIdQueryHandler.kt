package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.settings.api.queries.FindExpenseTypeByTenantIdQuery
import com.syc.dashboard.query.settings.dto.ExpenseTypeDto
import com.syc.dashboard.query.settings.repository.reactive.ExpenseTypeReactiveRepository
import reactor.core.publisher.Flux

class FindExpenseTypeByTenantIdQueryHandler(
    private val expenseTypeReactiveRepository: ExpenseTypeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindExpenseTypeByTenantIdQuery
        return expenseTypeReactiveRepository
            .findByTenantId(
                tenantId = query.tenantId,
            )
            .map { EntityDtoConversion.toDto(it, ExpenseTypeDto::class) }
    }
}
