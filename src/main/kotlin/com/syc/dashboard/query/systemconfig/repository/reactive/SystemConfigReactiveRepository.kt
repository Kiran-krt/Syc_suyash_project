package com.syc.dashboard.query.systemconfig.repository.reactive

import com.syc.dashboard.query.systemconfig.entity.SystemConfig
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface SystemConfigReactiveRepository : ReactiveMongoRepository<SystemConfig, String> {

    fun findByTenantId(tenantId: String): Mono<SystemConfig>
}
