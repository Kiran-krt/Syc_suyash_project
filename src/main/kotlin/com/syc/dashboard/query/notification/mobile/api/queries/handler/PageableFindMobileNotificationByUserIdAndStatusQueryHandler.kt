package com.syc.dashboard.query.notification.mobile.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum
import com.syc.dashboard.query.notification.mobile.api.queries.PageableFindMobileNotificationByUserIdAndStatusQuery
import com.syc.dashboard.query.notification.mobile.dto.MobileNotificationDto
import com.syc.dashboard.query.notification.mobile.repository.reactive.MobileNotificationReactiveRepository
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux

class PageableFindMobileNotificationByUserIdAndStatusQueryHandler constructor(
    private val mobileNotificationReactiveRepository: MobileNotificationReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableFindMobileNotificationByUserIdAndStatusQuery
        return mobileNotificationReactiveRepository
            .findByTenantIdAndUserIdAndStatusIn(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.status ?: MobileNotificationStatusEnum.values().toList(),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "creationDate",
                ),
            )
            .map { EntityDtoConversion.toDto(it, MobileNotificationDto::class) }
    }
}
