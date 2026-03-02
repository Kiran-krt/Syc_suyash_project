package com.syc.dashboard.command.timesheet.repository.jpa

import com.syc.dashboard.command.timesheet.entity.TimesheetEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TimesheetEventStoreRepository : MongoRepository<TimesheetEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<TimesheetEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq: :#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'eventData.userId': {\$eq: :#{#userId} }} " +
            " {'eventData.weekEndingDate': {\$eq: :#{#weekEndingDate} }} " +
            " ]} ",
    )
    fun findByTenantIdAndUserIdAndWeekEndingDate(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
        @Param("userId") userId: String,
        @Param("weekEndingDate") weekEndingDate: String,
    ): List<TimesheetEventModel>
}
