package com.syc.dashboard.query.notification.email.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.notification.email.api.queries.PageableFindEmailNotificationByUserIdAndStatusQuery
import com.syc.dashboard.query.notification.email.dto.EmailNotificationDto
import com.syc.dashboard.query.notification.email.repository.reactive.EmailNotificationReactiveRepository
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux

class PageableFindEmailNotificationByUserIdAndStatusQueryHandler constructor(
    private val emailNotificationReactiveRepository: EmailNotificationReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableFindEmailNotificationByUserIdAndStatusQuery
        return emailNotificationReactiveRepository
            .findByTenantIdAndUserIdAndStatusIn(
                tenantId = query.tenantId,
                userId = query.userId,
                status = query.status ?: EmailNotificationStatusEnum.values().toList(),
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "creationDate",
                ),
            )
            .map { EntityDtoConversion.toDto(it, EmailNotificationDto::class) }
    }
}
