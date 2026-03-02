package com.syc.dashboard.framework.core.infrastructure

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandlerMethod
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*

@Service
class QueryDispatcherImpl : QueryDispatcher {

    private val routes: MutableMap<Class<out BaseQuery>, MutableList<QueryHandlerMethod<BaseDto>>> =
        HashMap()

    override fun <T : BaseQuery> registerHandler(type: Class<T>, handler: (T: BaseQuery) -> Flux<out BaseDto>) {
        val handlers = routes.computeIfAbsent(type) { LinkedList() }
        handlers.add(handler)
    }

    override fun send(query: BaseQuery): Flux<out BaseDto> {
        val handlers = routes[query.javaClass]
        if (handlers == null || handlers.isEmpty()) {
            throw RuntimeException("No query handler was registered!")
        }
        if (handlers.size > 1) {
            throw RuntimeException("Cannot send query to more than one handler!")
        }
        return handlers[0].handle(query)
    }
}
