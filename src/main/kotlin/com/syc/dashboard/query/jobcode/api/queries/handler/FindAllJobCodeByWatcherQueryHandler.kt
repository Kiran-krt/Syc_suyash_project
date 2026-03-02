package com.syc.dashboard.query.jobcode.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.jobcode.api.queries.FindAllJobCodeByWatcherQuery
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.jobcode.repository.reactive.JobCodeReactiveRepository
import reactor.core.publisher.Flux

class FindAllJobCodeByWatcherQueryHandler(
    private val jobCodeReactiveRepository: JobCodeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindAllJobCodeByWatcherQuery
        return jobCodeReactiveRepository
            .findByTenantIdAndWatcherListContains(
                tenantId = query.tenantId,
                watcherId = query.watcherId,
            )
            .map { EntityDtoConversion.toDto(it, JobCodeDto::class) }
    }
}
