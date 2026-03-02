package com.syc.dashboard.framework.core.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import reactor.core.publisher.Flux

fun interface QueryHandlerMethod<out T : BaseDto> {
    fun handle(query: BaseQuery): Flux<out T>
}
