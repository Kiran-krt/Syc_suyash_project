package com.syc.dashboard.query.expensereport.repository.jpa

import com.syc.dashboard.query.expensereport.entity.ExpenseReportRow
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ExpenseReportRowRepository : MongoRepository<ExpenseReportRow, String> {

    fun findByTenantIdAndIdAndExpenseReportId(
        tenantId: String,
        id: String,
        expenseReportId: String,
    ): Optional<ExpenseReportRow>
}
