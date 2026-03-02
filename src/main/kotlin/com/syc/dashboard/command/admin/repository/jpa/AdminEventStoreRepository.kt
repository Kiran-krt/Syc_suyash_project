package com.syc.dashboard.command.admin.repository.jpa

import com.syc.dashboard.command.admin.entity.AdminEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AdminEventStoreRepository : MongoRepository<AdminEventModel, String> {

    fun findFirstByAggregateIdentifierOrderByVersionDesc(aggregateIdentifier: String): AdminEventModel

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<AdminEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq: :#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'eventData.email': {\$eq: :#{#email} }} " +
            " ]} ",
    )
    fun findByEventTypeAndEventDataEmail(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
        @Param("email") email: String,
    ): List<AdminEventModel>
}
