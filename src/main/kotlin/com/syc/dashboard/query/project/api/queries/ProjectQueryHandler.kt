package com.syc.dashboard.query.project.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.project.api.queries.handler.FindActiveProjectQueryHandler
import com.syc.dashboard.query.project.api.queries.handler.FindAllProjectQueryHandler
import com.syc.dashboard.query.project.api.queries.handler.FindProjectByIdQueryHandler
import com.syc.dashboard.query.project.api.queries.handler.PageableSearchAllProjectByFilterQueryHandler
import com.syc.dashboard.query.project.repository.reactive.ProjectReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ProjectQueryHandler @Autowired constructor(
    private val projectReactiveRepository: ProjectReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindProjectByIdQuery): Flux<out BaseDto> {
        return FindProjectByIdQueryHandler(projectReactiveRepository = projectReactiveRepository).handle(query)
    }

    private fun handle(query: FindAllProjectQuery): Flux<out BaseDto> {
        return FindAllProjectQueryHandler(projectReactiveRepository = projectReactiveRepository).handle(query)
    }

    private fun handle(query: PageableSearchAllProjectByFilterQuery): Flux<out BaseDto> {
        return PageableSearchAllProjectByFilterQueryHandler(projectReactiveRepository = projectReactiveRepository).handle(query)
    }

    private fun handle(query: FindAllActiveProjectQuery): Flux<out BaseDto> {
        return FindActiveProjectQueryHandler(projectReactiveRepository = projectReactiveRepository).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindProjectByIdQuery -> handle(query)
            is FindAllProjectQuery -> handle(query)
            is PageableSearchAllProjectByFilterQuery -> handle(query)
            is FindAllActiveProjectQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
