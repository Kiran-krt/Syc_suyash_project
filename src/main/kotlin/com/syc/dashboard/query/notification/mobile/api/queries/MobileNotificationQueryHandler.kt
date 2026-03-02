package com.syc.dashboard.query.notification.mobile.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.notification.mobile.api.queries.handler.FindMobileCountStatusQueryHandler
import com.syc.dashboard.query.notification.mobile.api.queries.handler.PageableFindMobileNotificationByUserIdAndStatusQueryHandler
import com.syc.dashboard.query.notification.mobile.repository.reactive.MobileNotificationReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class MobileNotificationQueryHandler @Autowired constructor(
    private val mobileNotificationReactiveRepository: MobileNotificationReactiveRepository,
) : QueryHandler {

    private fun handle(query: PageableFindMobileNotificationByUserIdAndStatusQuery): Flux<out BaseDto> {
        return PageableFindMobileNotificationByUserIdAndStatusQueryHandler(
            mobileNotificationReactiveRepository = mobileNotificationReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindMobileNotificationCountStatusQuery): Flux<out BaseDto> {
        return FindMobileCountStatusQueryHandler(
            mobileNotificationReactiveRepository = mobileNotificationReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is PageableFindMobileNotificationByUserIdAndStatusQuery -> handle(query)
            is FindMobileNotificationCountStatusQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
