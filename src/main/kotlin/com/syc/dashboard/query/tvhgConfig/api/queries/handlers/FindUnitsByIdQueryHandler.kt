package com.syc.dashboard.query.tvhgConfig.api.queries.handlers

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.tvhgConfig.api.queries.FindUnitsByIdQuery
import com.syc.dashboard.query.tvhgConfig.repository.reactive.TvhgConfigReactiveRepository
import reactor.core.publisher.Flux

class FindUnitsByIdQueryHandler(
    private val tvhgConfigReactiveRepository: TvhgConfigReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindUnitsByIdQuery

        return tvhgConfigReactiveRepository
            .findByTenantIdAndId(
                id = query.tenantId,
            )
    }
}
