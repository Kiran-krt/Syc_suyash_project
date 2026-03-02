package com.syc.dashboard.framework.core.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import reactor.core.publisher.Flux

interface QueryHandler {

    fun <T : BaseQuery> handle(query: T): Flux<out BaseDto>
}
