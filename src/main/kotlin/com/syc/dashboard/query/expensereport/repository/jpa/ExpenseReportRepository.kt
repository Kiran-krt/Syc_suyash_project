package com.syc.dashboard.query.expensereport.repository.jpa

import com.syc.dashboard.query.expensereport.entity.ExpenseReport
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ExpenseReportRepository : MongoRepository<ExpenseReport, String> {

    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, _id: :#{#id}} }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"employeeId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndId(
        tenantId: String,
        id: String,
    ): ExpenseReport?
}
