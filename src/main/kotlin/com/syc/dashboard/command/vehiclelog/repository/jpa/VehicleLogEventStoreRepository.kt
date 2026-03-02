package com.syc.dashboard.command.vehiclelog.repository.jpa

import com.syc.dashboard.command.vehiclelog.entity.VehicleLogEventModel
import org.springframework.data.mongodb.repository.MongoRepository

interface VehicleLogEventStoreRepository : MongoRepository<VehicleLogEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<VehicleLogEventModel>
}
