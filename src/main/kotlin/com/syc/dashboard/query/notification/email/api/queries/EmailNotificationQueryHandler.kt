package com.syc.dashboard.query.notification.email.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.notification.email.api.queries.handler.FindEmailCountStatusQueryHandler
import com.syc.dashboard.query.notification.email.api.queries.handler.PageableFindEmailNotificationByUserIdAndStatusQueryHandler
import com.syc.dashboard.query.notification.email.repository.reactive.EmailNotificationReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class EmailNotificationQueryHandler @Autowired constructor(
    private val emailNotificationReactiveRepository: EmailNotificationReactiveRepository,
) : QueryHandler {

    private fun handle(query: PageableFindEmailNotificationByUserIdAndStatusQuery): Flux<out BaseDto> {
        return PageableFindEmailNotificationByUserIdAndStatusQueryHandler(
            emailNotificationReactiveRepository = emailNotificationReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindEmailNotificationCountStatusQuery): Flux<out BaseDto> {
        return FindEmailCountStatusQueryHandler(
            emailNotificationReactiveRepository = emailNotificationReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is PageableFindEmailNotificationByUserIdAndStatusQuery -> handle(query)
            is FindEmailNotificationCountStatusQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
