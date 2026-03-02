package com.syc.dashboard.query.settings.repository.reactive

import com.syc.dashboard.query.settings.entity.Settings
import com.syc.dashboard.query.settings.entity.enums.SettingsStatusEnum
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface SettingsReactiveRepository : ReactiveMongoRepository<Settings, String> {
    fun findByTenantIdAndId(tenantId: String, id: String): Mono<Settings>
    fun findFirstByTenantIdAndStatus(
        tenantId: String,
        status: SettingsStatusEnum = SettingsStatusEnum.ACTIVE,
    ): Mono<Settings>

    fun findByTenantId(tenantId: String): Mono<Settings>
}
