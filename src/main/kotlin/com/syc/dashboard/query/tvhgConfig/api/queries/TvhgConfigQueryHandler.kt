package com.syc.dashboard.query.tvhgConfig.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.expensereport.api.queries.handler.*
import com.syc.dashboard.query.projectreport.api.queries.handler.*
import com.syc.dashboard.query.tvhgConfig.api.queries.handlers.FindDesignStormByIdQueryHandler
import com.syc.dashboard.query.tvhgConfig.api.queries.handlers.FindMdStandardNumberByIdQueryHandler
import com.syc.dashboard.query.tvhgConfig.api.queries.handlers.FindTvhgConfigQueryHandler
import com.syc.dashboard.query.tvhgConfig.api.queries.handlers.FindUnitsByIdQueryHandler
import com.syc.dashboard.query.tvhgConfig.repository.reactive.TvhgConfigReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class TvhgConfigQueryHandler @Autowired constructor(
    private val tvhgConfigReactiveRepository: TvhgConfigReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindUnitsByIdQuery): Flux<out BaseDto> {
        return FindUnitsByIdQueryHandler(
            tvhgConfigReactiveRepository = tvhgConfigReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindTvhgConfigQuery): Flux<out BaseDto> {
        return FindTvhgConfigQueryHandler(
            tvhgConfigReactiveRepository = tvhgConfigReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindDesignStormByIdQuery): Flux<out BaseDto> {
        return FindDesignStormByIdQueryHandler(
            tvhgConfigReactiveRepository = tvhgConfigReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindMdStandardNumberByIdQuery): Flux<out BaseDto> {
        return FindMdStandardNumberByIdQueryHandler(
            tvhgConfigReactiveRepository = tvhgConfigReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindUnitsByIdQuery -> handle(query)
            is FindTvhgConfigQuery -> handle(query)
            is FindDesignStormByIdQuery -> handle(query)
            is FindMdStandardNumberByIdQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
