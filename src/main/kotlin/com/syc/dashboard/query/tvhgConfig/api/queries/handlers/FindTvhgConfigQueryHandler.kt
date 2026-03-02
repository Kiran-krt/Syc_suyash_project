package com.syc.dashboard.query.tvhgConfig.api.queries.handlers

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.tvhgConfig.api.queries.FindTvhgConfigQuery
import com.syc.dashboard.query.tvhgConfig.dto.TvhgConfigDto
import com.syc.dashboard.query.tvhgConfig.repository.reactive.TvhgConfigReactiveRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class FindTvhgConfigQueryHandler(
    private val tvhgConfigReactiveRepository: TvhgConfigReactiveRepository,
) : QueryHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindTvhgConfigQuery

        return tvhgConfigReactiveRepository
            .findByTenantId(tenantId = query.tenantId)
            .map { EntityDtoConversion.copyFromJson(it, TvhgConfigDto::class.java) }
            .switchIfEmpty {
                log.warn("Tvhg Configuration not found for tenant ${query.tenantId}")
                Mono.just(TvhgConfigDto())
            }
            .flux()
    }
}
