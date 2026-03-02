package com.syc.dashboard.command.jobcode.repository

import com.syc.dashboard.command.jobcode.entity.JobCodeEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param

interface JobCodeEventStoreRepository : MongoRepository<JobCodeEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<JobCodeEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq: :#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'eventData.code': {\$eq: :#{#code} }} " +
            " ]} ",
    )
    fun findByEventTypeAndEventDataId(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
        @Param("code") code: String,
    ): List<JobCodeEventModel>
}
