package com.syc.dashboard.query.timesheet.repository.jpa

import com.syc.dashboard.query.timesheet.entity.Timesheet
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TimesheetRepository : MongoRepository<Timesheet, String> {
    @Aggregation(
        "{\$match: " +
            "{tenantId: :#{#tenantId}, _id: :#{#id}} }",
        "{\$lookup: " +
            "{ from: \"q_employee\", " +
            "localField: \"userId\", " +
            "foreignField: \"_id\", " +
            "as: \"employeeInfo\"" +
            "} } ",
        "{\$unwind: { path: \$employeeInfo, preserveNullAndEmptyArrays: true }}",
    )
    fun findByTenantIdAndId(tenantId: String, id: String): Timesheet?
}
