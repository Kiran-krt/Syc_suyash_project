package com.syc.dashboard.query.tvhgConfig.api.queries.handlers

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.tvhgConfig.api.queries.FindDesignStormByIdQuery
import com.syc.dashboard.query.tvhgConfig.repository.reactive.TvhgConfigReactiveRepository
import reactor.core.publisher.Flux

class FindDesignStormByIdQueryHandler(
    private val tvhgConfigReactiveRepository: TvhgConfigReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindDesignStormByIdQuery

        return tvhgConfigReactiveRepository
            .findAllByTenantIdAndId(
                id = query.tenantId,
            )
    }
}
