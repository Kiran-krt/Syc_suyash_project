package com.syc.dashboard.query.settings.repository.reactive

import com.syc.dashboard.query.settings.entity.ExpenseType
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ExpenseTypeReactiveRepository : ReactiveMongoRepository<ExpenseType, String> {

    fun findByTenantId(
        tenantId: String,
    ): Flux<ExpenseType>
}
