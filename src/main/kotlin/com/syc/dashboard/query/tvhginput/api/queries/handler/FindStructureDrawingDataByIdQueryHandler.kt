package com.syc.dashboard.query.tvhginput.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.tvhginput.api.queries.FindStructureDrawingDataByIdQuery
import com.syc.dashboard.query.tvhginput.repository.reactive.TvhgInputReactiveRepository
import reactor.core.publisher.Flux

class FindStructureDrawingDataByIdQueryHandler(
    private val tvhgInputReactiveRepository: TvhgInputReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindStructureDrawingDataByIdQuery

        return tvhgInputReactiveRepository
            .findByTenantIdAndIdAndStructureDrawingDataId(
                tenantId = query.tenantId,
                id = query.id,
                structureDrawingDataId = query.structureDrawingDataId,
            ).flux()
    }
}
