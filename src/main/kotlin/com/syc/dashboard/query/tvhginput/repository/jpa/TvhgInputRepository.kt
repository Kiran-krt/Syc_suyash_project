package com.syc.dashboard.query.tvhginput.repository.jpa

import com.syc.dashboard.query.tvhginput.entity.TvhgInput
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TvhgInputRepository : MongoRepository<TvhgInput, String> {

    fun findByTenantIdAndId(
        tenantId: String,
        id: String,
    ): TvhgInput
}
