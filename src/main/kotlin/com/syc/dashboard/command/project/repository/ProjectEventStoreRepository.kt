package com.syc.dashboard.command.project.repository

import com.syc.dashboard.command.project.entity.ProjectEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param

interface ProjectEventStoreRepository : MongoRepository<ProjectEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<ProjectEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq: :#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'eventData.projectCode': {\$eq: :#{#projectCode} }} " +
            " ]} ",
    )
    fun findByEventTypeAndEventDataId(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
        @Param("projectCode") projectCode: String,
    ): List<ProjectEventModel>
}
