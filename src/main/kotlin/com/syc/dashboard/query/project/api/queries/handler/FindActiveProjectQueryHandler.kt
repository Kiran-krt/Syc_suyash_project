package com.syc.dashboard.query.project.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.project.api.queries.FindAllActiveProjectQuery
import com.syc.dashboard.query.project.dto.ProjectDto
import com.syc.dashboard.query.project.entity.enums.ProjectStatusEnum
import com.syc.dashboard.query.project.repository.reactive.ProjectReactiveRepository
import reactor.core.publisher.Flux

class FindActiveProjectQueryHandler(
    private val projectReactiveRepository: ProjectReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindAllActiveProjectQuery
        return projectReactiveRepository
            .findAllByTenantIdAndStatusOrderByProjectCodeAsc(
                tenantId = query.tenantId,
                status = ProjectStatusEnum.ACTIVE,
            )
            .map {
                EntityDtoConversion.toDto(it, ProjectDto::class)
            }
    }
}
