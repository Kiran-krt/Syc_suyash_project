package com.syc.dashboard.query.projectreport.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.expensereport.api.queries.handler.*
import com.syc.dashboard.query.projectreport.api.queries.handler.*
import com.syc.dashboard.query.projectreport.repository.reactive.ProjectReportReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ProjectReportQueryHandler @Autowired constructor(
    private val projectReportReactiveRepository: ProjectReportReactiveRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindProjectReportByIdQuery): Flux<out BaseDto> {
        return FindProjectReportByIdQueryHandler(
            projectReportReactiveRepository = projectReportReactiveRepository,
            adminReactiveRepository = adminReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: PageableSearchProjectReportByFilterQuery): Flux<out BaseDto> {
        return PageableSearchProjectReportByFilterQueryHandler(
            projectReportReactiveRepository = projectReportReactiveRepository,
            adminReactiveRepository = adminReactiveRepository,
        ).handle(query)
    }
    private fun handle(query: FindAllProjectReportQuery): Flux<out BaseDto> {
        return FindAllProjectReportQueryHandler(
            projectReportReactiveRepository = projectReportReactiveRepository,
            adminReactiveRepository = adminReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindOutfallPhotoByIdQuery): Flux<out BaseDto> {
        return FindOutfallPhotoByIdQueryHandler(
            projectReportReactiveRepository = projectReportReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindAppendixByIdQuery): Flux<out BaseDto> {
        return FindAppendixByIdQueryHandler(
            projectReportReactiveRepository = projectReportReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindProjectReportByIdQuery -> handle(query)
            is PageableSearchProjectReportByFilterQuery -> handle(query)
            is FindAllProjectReportQuery -> handle(query)
            is FindOutfallPhotoByIdQuery -> handle(query)
            is FindAppendixByIdQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
