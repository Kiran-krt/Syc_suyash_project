package com.syc.dashboard.query.systemconfig.api.query.handlers

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.systemconfig.api.query.FindUISystemConfigQuery
import com.syc.dashboard.query.systemconfig.dto.UISystemConfigDto
import com.syc.dashboard.query.systemconfig.repository.reactive.SystemConfigReactiveRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class FindUISystemConfigQueryHandler(
    private val systemConfigReactiveRepository: SystemConfigReactiveRepository,
) : QueryHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindUISystemConfigQuery

        return systemConfigReactiveRepository
            .findByTenantId(tenantId = query.tenantId)
            .map { EntityDtoConversion.copyFromJson(it, UISystemConfigDto::class.java) }
            .switchIfEmpty {
                log.warn("System Configuration not found for UI for tenant ${query.tenantId}")
                Mono.just(UISystemConfigDto())
            }
            .flux()
    }
}
