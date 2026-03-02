package com.syc.dashboard.query.document.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.document.api.queries.FindDocumentByIdQuery
import com.syc.dashboard.query.document.dto.DocumentDto
import com.syc.dashboard.query.document.repository.reactive.DocumentReactiveRepository
import reactor.core.publisher.Flux

class FindDocumentByIdQueryHandler(
    private val documentReactiveRepository: DocumentReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindDocumentByIdQuery
        return documentReactiveRepository
            .findByTenantIdAndId(
                tenantId = query.tenantId,
                id = query.id,
            )
            .map { EntityDtoConversion.toDto(it, DocumentDto::class) }
            .flux()
    }
}
