package com.syc.dashboard.query.systemconfig.api.query

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.systemconfig.api.query.handlers.FindSystemConfigQueryHandler
import com.syc.dashboard.query.systemconfig.api.query.handlers.FindUISystemConfigQueryHandler
import com.syc.dashboard.query.systemconfig.repository.reactive.SystemConfigReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SystemConfigQueryHandler @Autowired constructor(
    private val systemConfigReactiveRepository: SystemConfigReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindSystemConfigQuery): Flux<out BaseDto> {
        return FindSystemConfigQueryHandler(systemConfigReactiveRepository = systemConfigReactiveRepository)
            .handle(query)
    }

    private fun handle(query: FindUISystemConfigQuery): Flux<out BaseDto> {
        return FindUISystemConfigQueryHandler(systemConfigReactiveRepository = systemConfigReactiveRepository)
            .handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindSystemConfigQuery -> handle(query)
            is FindUISystemConfigQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
