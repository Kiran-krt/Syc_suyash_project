package com.syc.dashboard.command.expensereport.repository.jpa

import com.syc.dashboard.command.expensereport.entity.ExpenseReportEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExpenseReportEventStoreRepository : MongoRepository<ExpenseReportEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<ExpenseReportEventModel>

    @Query(
        " {\$and: [ " +
            " {'eventType': {\$eq: :#{#eventType} }}, " +
            " {'eventData.tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'eventData.periodFrom': {\$eq: :#{#periodFrom} }}, " +
            " {'eventData.employeeId': {\$eq: :#{#employeeId} }} " +
            " ]} ",
    )
    fun findByEventTypeAndEventDataPeriodFromAndEmployeeId(
        @Param("eventType") eventType: String,
        @Param("tenantId") tenantId: String,
        @Param("periodFrom") periodFrom: String,
        @Param("employeeId") employeeId: String,
    ): List<ExpenseReportEventModel>
}
