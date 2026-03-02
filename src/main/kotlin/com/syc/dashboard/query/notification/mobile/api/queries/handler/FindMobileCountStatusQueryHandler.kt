package com.syc.dashboard.query.notification.mobile.api.queries.handler

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.notification.mobile.api.queries.FindMobileNotificationCountStatusQuery
import com.syc.dashboard.query.notification.mobile.repository.reactive.MobileNotificationReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindMobileCountStatusQueryHandler constructor(
    private val mobileNotificationReactiveRepository: MobileNotificationReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindMobileNotificationCountStatusQuery
        return mobileNotificationReactiveRepository
            .mobileNotificationCountByTenantIdAndUserIdAndStatus(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }
}
