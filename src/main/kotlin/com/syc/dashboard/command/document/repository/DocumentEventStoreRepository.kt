package com.syc.dashboard.command.document.repository

import com.syc.dashboard.command.document.entity.DocumentEventModel
import org.springframework.data.mongodb.repository.MongoRepository

interface DocumentEventStoreRepository : MongoRepository<DocumentEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<DocumentEventModel>
}
