package com.syc.dashboard.query.notification.inapp.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.notification.inapp.api.queries.handler.FindInAppNotificationCountStatusQueryHandler
import com.syc.dashboard.query.notification.inapp.api.queries.handler.PageableFindInAppNotificationByUserIdAndStatusQueryHandler
import com.syc.dashboard.query.notification.inapp.repository.reactive.InAppNotificationReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class InAppNotificationQueryHandler @Autowired constructor(
    private val inAppNotificationReactiveRepository: InAppNotificationReactiveRepository,
) : QueryHandler {

    private fun handle(query: PageableFindInAppNotificationByUserIdAndStatusQuery): Flux<out BaseDto> {
        return PageableFindInAppNotificationByUserIdAndStatusQueryHandler(
            inAppNotificationReactiveRepository = inAppNotificationReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindInAppNotificationCountStatusQuery): Flux<out BaseDto> {
        return FindInAppNotificationCountStatusQueryHandler(
            inAppNotificationReactiveRepository = inAppNotificationReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is PageableFindInAppNotificationByUserIdAndStatusQuery -> handle(query)
            is FindInAppNotificationCountStatusQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
