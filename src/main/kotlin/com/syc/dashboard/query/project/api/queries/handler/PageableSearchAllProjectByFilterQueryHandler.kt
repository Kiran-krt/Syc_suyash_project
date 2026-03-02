package com.syc.dashboard.query.project.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.project.api.queries.PageableSearchAllProjectByFilterQuery
import com.syc.dashboard.query.project.dto.ProjectDto
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import com.syc.dashboard.query.project.repository.reactive.ProjectReactiveRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux

class PageableSearchAllProjectByFilterQueryHandler(
    private val projectReactiveRepository: ProjectReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableSearchAllProjectByFilterQuery

        return projectReactiveRepository
            .findByTenantIdAndProjectCodeContains(
                tenantId = query.tenantId,
                projectCode = query.projectCode,
                status = query.status.ifEmpty { ProjectStatusEnum.entries },
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    Sort.by(query.sort, query.sortBy),
                ),
            ).map { EntityDtoConversion.toDto(it, ProjectDto::class) }
    }
}
