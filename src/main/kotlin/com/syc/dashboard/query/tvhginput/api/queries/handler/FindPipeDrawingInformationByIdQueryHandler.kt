package com.syc.dashboard.query.tvhginput.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.tvhginput.api.queries.FindPipeDrawingInformationByIdQuery
import com.syc.dashboard.query.tvhginput.repository.reactive.TvhgInputReactiveRepository
import reactor.core.publisher.Flux

class FindPipeDrawingInformationByIdQueryHandler(
    private val tvhgInputReactiveRepository: TvhgInputReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindPipeDrawingInformationByIdQuery

        return tvhgInputReactiveRepository
            .findByTenantIdAndIdAndPipeDrawingInformationId(
                tenantId = query.tenantId,
                id = query.id,
                pipeDrawingInformationId = query.pipeDrawingInformationId,
            ).flux()
    }
}
