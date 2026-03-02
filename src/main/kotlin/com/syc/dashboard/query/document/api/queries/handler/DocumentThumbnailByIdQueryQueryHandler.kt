package com.syc.dashboard.query.document.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.document.api.queries.DocumentThumbnailByIdQuery
import com.syc.dashboard.query.document.dto.DocumentDto
import com.syc.dashboard.query.document.repository.reactive.DocumentReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class DocumentThumbnailByIdQueryQueryHandler(
    private val documentReactiveRepository: DocumentReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as DocumentThumbnailByIdQuery
        return documentReactiveRepository
            .findByTenantIdAndId(
                tenantId = query.tenantId,
                id = query.id,
            )
            .map { EntityDtoConversion.toDto(it, DocumentDto::class) }
            .toFlux()
    }
}
