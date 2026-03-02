package com.syc.dashboard.query.notification.inapp.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum
import com.syc.dashboard.query.notification.inapp.api.queries.PageableFindInAppNotificationByUserIdAndStatusQuery
import com.syc.dashboard.query.notification.inapp.dto.InAppNotificationDto
import com.syc.dashboard.query.notification.inapp.repository.reactive.InAppNotificationReactiveRepository
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux

class PageableFindInAppNotificationByUserIdAndStatusQueryHandler constructor(
    private val inAppNotificationReactiveRepository: InAppNotificationReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableFindInAppNotificationByUserIdAndStatusQuery
        return inAppNotificationReactiveRepository
            .findByTenantIdAndUserIdAndStatusIn(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.status ?: InAppNotificationStatusEnum.values().toList(),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "creationDate",
                ),
            )
            .map { EntityDtoConversion.toDto(it, InAppNotificationDto::class) }
    }
}
