package com.syc.dashboard.query.document.api.queries

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.document.api.queries.handler.DocumentThumbnailByIdQueryQueryHandler
import com.syc.dashboard.query.document.api.queries.handler.DocumentViewByIdQueryQueryHandler
import com.syc.dashboard.query.document.api.queries.handler.FindDocumentByIdQueryHandler
import com.syc.dashboard.query.document.repository.reactive.DocumentReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class DocumentQueryHandler @Autowired constructor(
    private val documentReactiveRepository: DocumentReactiveRepository,
) : QueryHandler {

    private fun handle(query: DocumentViewByIdQuery): Flux<out BaseDto> {
        return DocumentViewByIdQueryQueryHandler(
            documentReactiveRepository = documentReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: DocumentThumbnailByIdQuery): Flux<out BaseDto> {
        return DocumentThumbnailByIdQueryQueryHandler(
            documentReactiveRepository = documentReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindDocumentByIdQuery): Flux<out BaseDto> {
        return FindDocumentByIdQueryHandler(
            documentReactiveRepository = documentReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is DocumentViewByIdQuery -> handle(query)
            is DocumentThumbnailByIdQuery -> handle(query)
            is FindDocumentByIdQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
