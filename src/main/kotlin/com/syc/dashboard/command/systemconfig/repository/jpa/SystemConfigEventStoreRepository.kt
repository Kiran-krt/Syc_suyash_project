package com.syc.dashboard.command.systemconfig.repository.jpa

import com.syc.dashboard.command.systemconfig.entity.SystemConfigEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SystemConfigEventStoreRepository : MongoRepository<SystemConfigEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<SystemConfigEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq: :#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }} " +
            " ]} ",
    )
    fun findByEventTypeAndEventDataTenant(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
    ): List<SystemConfigEventModel>
}
