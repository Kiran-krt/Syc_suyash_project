package com.syc.dashboard.query.settings.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.settings.api.queries.FindPayrollItemByTenantIdQuery
import com.syc.dashboard.query.settings.dto.PayrollItemDto
import com.syc.dashboard.query.settings.repository.reactive.PayrollItemReactiveRepository
import reactor.core.publisher.Flux

class FindPayrollItemByTenantIdQueryHandler(
    private val payrollItemReactiveRepository: PayrollItemReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindPayrollItemByTenantIdQuery
        return payrollItemReactiveRepository
            .findByTenantId(
                tenantId = query.tenantId,
            )
            .map { EntityDtoConversion.toDto(it, PayrollItemDto::class) }
    }
}
