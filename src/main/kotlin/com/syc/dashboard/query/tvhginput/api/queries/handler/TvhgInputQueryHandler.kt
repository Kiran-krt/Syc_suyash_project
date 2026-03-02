package com.syc.dashboard.query.tvhginput.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.employee.repository.reactive.EmployeeReactiveRepository
import com.syc.dashboard.query.tvhginput.api.queries.*
import com.syc.dashboard.query.tvhginput.repository.reactive.TvhgInputReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class TvhgInputQueryHandler @Autowired constructor(
    private val tvhgInputReactiveRepository: TvhgInputReactiveRepository,
    private val employeeReactiveRepository: EmployeeReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindTvhgInputByIdQuery): Flux<out BaseDto> {
        return FindTvhgInputByIdQueryHandler(
            tvhgInputReactiveRepository = tvhgInputReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindTvhgInputByTenantIdQuery): Flux<out BaseDto> {
        return FindTvhgInputByTenantIdQueryHandler(
            tvhgInputReactiveRepository = tvhgInputReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: PageableSearchAllTvhgInputQuery): Flux<out BaseDto> {
        return PageableSearchTvhgInputQueryHandler(
            tvhgInputReactiveRepository = tvhgInputReactiveRepository,
            employeeReactiveRepository = employeeReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindStructureDrawingDataByIdQuery): Flux<out BaseDto> {
        return FindStructureDrawingDataByIdQueryHandler(
            tvhgInputReactiveRepository = tvhgInputReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindProjectInformationByIdQuery): Flux<out BaseDto> {
        return FindProjectInformationByIdQueryHandler(
            tvhgInputReactiveRepository = tvhgInputReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindHydrologicInformationByIdQuery): Flux<out BaseDto> {
        return FindHydrologicInformationByIdQueryHandler(
            tvhgInputReactiveRepository = tvhgInputReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindPipeDrawingInformationByIdQuery): Flux<out BaseDto> {
        return FindPipeDrawingInformationByIdQueryHandler(
            tvhgInputReactiveRepository = tvhgInputReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindTvhgInputByIdQuery -> handle(query)
            is FindTvhgInputByTenantIdQuery -> handle(query)
            is PageableSearchAllTvhgInputQuery -> handle(query)
            is FindStructureDrawingDataByIdQuery -> handle(query)
            is FindProjectInformationByIdQuery -> handle(query)
            is FindHydrologicInformationByIdQuery -> handle(query)
            is FindPipeDrawingInformationByIdQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
