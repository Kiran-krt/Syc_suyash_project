package com.syc.dashboard.query.tvhgConfig.repository.jpa

import com.syc.dashboard.query.tvhgConfig.entity.TvhgConfig
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TvhgConfigRepository : MongoRepository<TvhgConfig, String> {

    fun findByTenantIdAndId(
        tenantId: String,
        id: String,
    ): TvhgConfig
}
