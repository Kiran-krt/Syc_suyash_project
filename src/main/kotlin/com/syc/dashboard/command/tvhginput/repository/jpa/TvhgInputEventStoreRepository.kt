package com.syc.dashboard.command.tvhginput.repository.jpa

import com.syc.dashboard.command.tvhginput.entity.TvhgInputEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TvhgInputEventStoreRepository : MongoRepository<TvhgInputEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<TvhgInputEventModel>
}
