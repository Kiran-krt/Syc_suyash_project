package com.syc.dashboard.query.tvhginput.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.tvhginput.api.queries.FindHydrologicInformationByIdQuery
import com.syc.dashboard.query.tvhginput.repository.reactive.TvhgInputReactiveRepository
import reactor.core.publisher.Flux

class FindHydrologicInformationByIdQueryHandler(
    private val tvhgInputReactiveRepository: TvhgInputReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindHydrologicInformationByIdQuery

        return tvhgInputReactiveRepository
            .findHydrologicInformationByTenantIdAndId(
                tenantId = query.tenantId,
                id = query.id,
            ).flux()
    }
}
