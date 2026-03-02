package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.settings.api.queries.FindPayrollItemByIdQuery
import com.syc.dashboard.query.settings.dto.PayrollItemDto
import com.syc.dashboard.query.settings.exceptions.PayrollItemNotFoundException
import com.syc.dashboard.query.settings.repository.reactive.PayrollItemReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.switchIfEmpty

class FindPayrollItemByIdQueryHandler(
    private val payrollItemReactiveRepository: PayrollItemReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindPayrollItemByIdQuery
        return payrollItemReactiveRepository
            .findByTenantIdAndId(query.tenantId, query.id)
            .map { EntityDtoConversion.toDto(it, PayrollItemDto::class) }
            .switchIfEmpty { throw PayrollItemNotFoundException("Payroll Item not found with id ${query.id}.") }
            .flux()
    }
}
