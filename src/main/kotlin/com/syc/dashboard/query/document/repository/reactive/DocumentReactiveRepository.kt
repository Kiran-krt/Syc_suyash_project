package com.syc.dashboard.query.document.repository.reactive

import com.syc.dashboard.query.document.entity.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface DocumentReactiveRepository : ReactiveMongoRepository<Document, String> {

    fun findByTenantIdAndId(tenantId: String, id: String): Mono<Document>
}
