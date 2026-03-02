package com.syc.dashboard.query.jobcode.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.jobcode.api.queries.SearchJobCodeByCodeQuery
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.jobcode.entity.enums.JobCodeStatusEnum
import com.syc.dashboard.query.jobcode.repository.reactive.JobCodeReactiveRepository
import reactor.core.publisher.Flux

class SearchJobCodeByCodeQueryHandler(
    private val jobCodeReactiveRepository: JobCodeReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchJobCodeByCodeQuery
        return jobCodeReactiveRepository
            .findByTenantIdAndCodeContainsAndStatusIn(
                tenantId = query.tenantId,
                code = query.code,
                status = query.status ?: JobCodeStatusEnum.values().toList(),
            )
            .map { EntityDtoConversion.toDto(it, JobCodeDto::class) }
    }
}
