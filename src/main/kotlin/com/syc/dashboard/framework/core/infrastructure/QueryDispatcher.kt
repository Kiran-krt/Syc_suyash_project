package com.syc.dashboard.framework.core.infrastructure

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import reactor.core.publisher.Flux

interface QueryDispatcher {
    fun <T> registerHandler(type: Class<T>, handler: (T: BaseQuery) -> Flux<out BaseDto>) where T : BaseQuery
    fun send(query: BaseQuery): Flux<out BaseDto>
}
