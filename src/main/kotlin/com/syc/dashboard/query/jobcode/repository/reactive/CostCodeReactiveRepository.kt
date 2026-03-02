package com.syc.dashboard.query.jobcode.repository.reactive

import com.syc.dashboard.query.jobcode.entity.CostCode
import com.syc.dashboard.query.jobcode.entity.enums.CostCodeStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CostCodeReactiveRepository : ReactiveMongoRepository<CostCode, String> {
    fun findByTenantIdAndJobCodeId(
        tenantId: String,
        jobCodeId: String,
        pageable: Pageable,
    ): Flux<CostCode>

    fun findByTenantIdAndJobCodeIdAndStatus(
        tenantId: String,
        jobCodeId: String,
        status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
        pageable: Pageable,
    ): Flux<CostCode>

    fun findByTenantIdAndStatusAndJobCodeId(
        tenantId: String,
        status: CostCodeStatusEnum = CostCodeStatusEnum.ACTIVE,
        jobCodeId: String,
    ): Flux<CostCode>

    fun findByTenantIdAndJobCodeId(
        tenantId: String,
        jobCodeId: String,
    ): Flux<CostCode>

    fun findByTenantIdAndJobCodeIdAndId(
        tenantId: String,
        jobCodeId: String,
        id: String,
    ): Mono<CostCode>

    fun findByTenantIdAndJobCodeIdAndCodeContains(
        tenantId: String,
        jobCodeId: String,
        code: String,
    ): Flux<CostCode>

    fun findByTenantIdAndCodeContainsAndStatusIn(
        tenantId: String,
        code: String,
        status: List<CostCodeStatusEnum>,
    ): Flux<CostCode>
}
