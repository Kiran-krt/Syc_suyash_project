package com.syc.dashboard.query.projectreport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.projectreport.api.queries.FindAppendixByIdQuery
import com.syc.dashboard.query.projectreport.repository.reactive.ProjectReportReactiveRepository
import reactor.core.publisher.Flux

class FindAppendixByIdQueryHandler(
    private val projectReportReactiveRepository: ProjectReportReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindAppendixByIdQuery

        return projectReportReactiveRepository
            .findByTenantIdAndIdAndAppendixList(
                tenantId = query.tenantId,
                id = query.id,
            )
    }
}
