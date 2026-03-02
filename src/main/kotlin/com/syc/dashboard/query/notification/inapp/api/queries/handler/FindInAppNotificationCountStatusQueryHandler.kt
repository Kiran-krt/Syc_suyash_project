package com.syc.dashboard.query.notification.inapp.api.queries.handler

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.notification.inapp.api.queries.FindInAppNotificationCountStatusQuery
import com.syc.dashboard.query.notification.inapp.repository.reactive.InAppNotificationReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindInAppNotificationCountStatusQueryHandler constructor(
    private val inAppNotificationReactiveRepository: InAppNotificationReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindInAppNotificationCountStatusQuery
        return inAppNotificationReactiveRepository
            .inAppNotificationCountByTenantIdAndUserIdAndStatus(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }
}
