package com.syc.dashboard.command.employee.repository.jpa

import com.syc.dashboard.command.employee.entity.EmployeeEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param

interface EmployeeEventStoreRepository : MongoRepository<EmployeeEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<EmployeeEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq::#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'eventData.email': {\$eq: :#{#email} }} " +
            " ]} ",
    )
    fun findByEventTypeAndEventDataEmail(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
        @Param("email") email: String,
    ): List<EmployeeEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq::#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'eventData.email': {\$eq: :#{#email} }} " +
            " ]} ",
    )
    fun findFirstByEventTypeAndEventDataEmail(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
        @Param("email") email: String,
    ): EmployeeEventModel?
}
