package com.syc.dashboard.command.settings.repository.jpa

import com.syc.dashboard.command.settings.entity.SettingsEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SettingsEventStoreRepository : MongoRepository<SettingsEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<SettingsEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq: :#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }}, " +
            " ]} ",
    )
    fun findByEventTypeAndEventDataTenantId(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
    ): List<SettingsEventModel>
}
