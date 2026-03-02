package com.syc.dashboard.query.tvhginput.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.tvhginput.api.queries.FindTvhgInputByIdQuery
import com.syc.dashboard.query.tvhginput.dto.TvhgInputDto
import com.syc.dashboard.query.tvhginput.repository.reactive.TvhgInputReactiveRepository
import reactor.core.publisher.Flux

class FindTvhgInputByIdQueryHandler(
    private val tvhgInputReactiveRepository: TvhgInputReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindTvhgInputByIdQuery

        return tvhgInputReactiveRepository
            .findByTenantIdAndId(
                tenantId = query.tenantId,
                id = query.id,
            )
            .map { EntityDtoConversion.toDto(it, TvhgInputDto::class) }
            .flux()
    }
}
