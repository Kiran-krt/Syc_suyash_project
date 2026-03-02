package com.syc.dashboard.query.admin.repository.reactive

import com.syc.dashboard.query.admin.entity.Admin
import com.syc.dashboard.query.admin.entity.enums.AdminStatusEnum
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface AdminReactiveRepository : ReactiveMongoRepository<Admin, String> {
    fun findByTenantIdAndId(tenantId: String, id: String): Mono<Admin>

    fun findFirstByTenantIdAndEmail(tenantId: String, email: String): Mono<Admin>

    fun findByTenantIdAndFirstNameContainsIgnoreCaseOrderByFirstName(
        tenantId: String,
        firstName: String,
    ): Flux<Admin>

    fun findAllByTenantIdAndStatusIn(
        tenantId: String,
        status: AdminStatusEnum = AdminStatusEnum.ACTIVE,
    ): Flux<Admin>
}
