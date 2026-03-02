package com.syc.dashboard.query.expensereport.entity

import com.syc.dashboard.framework.core.entity.BaseEntity
import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.employee.entity.Employee
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_expense_report")
class ExpenseReport(
    @Id
    val id: String,
    var periodFrom: String = "",
    var periodTo: String = "",
    var employeeId: String = "",
    var employeeInfo: Employee? = null,
    var supervisorId: String = "",
    var supervisorInfo: BaseEntity? = null,
    var description: String = "",
    var status: ExpenseReportStatusEnum,
    var createdOn: Date = Date(),
    var updatedOn: Date = Date(),
    var commentsByAdmin: String = "",
    var commentsByEmployee: String = "",
    var commentsBySupervisor: String = "",
    var adminSignature: String = "",
    var employeeSignature: String = "",
    var supervisorSignature: String = "",
    var expenseReportRowsForEmployee: List<ExpenseReportRow>? = null,
    var expenseReportRowsForSuyash: List<ExpenseReportRow>? = null,
) : TenantBaseEntity()
