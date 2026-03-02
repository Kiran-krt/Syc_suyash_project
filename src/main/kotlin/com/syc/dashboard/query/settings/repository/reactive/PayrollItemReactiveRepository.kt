package com.syc.dashboard.query.settings.repository.reactive

import com.syc.dashboard.query.settings.entity.PayrollItem
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PayrollItemReactiveRepository : ReactiveMongoRepository<PayrollItem, String> {

    fun findByTenantId(
        tenantId: String,
    ): Flux<PayrollItem>

    fun findByTenantIdAndId(tenantId: String, id: String): Mono<PayrollItem>
}
