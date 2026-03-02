package com.syc.dashboard.query.notification.email.api.queries.handler

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.notification.email.api.queries.FindEmailNotificationCountStatusQuery
import com.syc.dashboard.query.notification.email.repository.reactive.EmailNotificationReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindEmailCountStatusQueryHandler constructor(
    private val emailNotificationReactiveRepository: EmailNotificationReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindEmailNotificationCountStatusQuery
        return emailNotificationReactiveRepository
            .emailNotificationCountByTenantIdAndUserIdAndStatus(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }
}
