package com.syc.dashboard.query.expensereport.entity

import com.syc.dashboard.framework.core.entity.TenantBaseEntity
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseByEnum
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportRowStatusEnum
import com.syc.dashboard.query.jobcode.entity.CostCode
import com.syc.dashboard.query.jobcode.entity.JobCode
import com.syc.dashboard.query.settings.entity.ExpenseType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "q_expense_report_row")
class ExpenseReportRow(
    @Id
    val id: String,
    var expenseReportId: String = "",
    var expenseReportInfo: ExpenseReport? = null,
    var expenseTypeId: String = "",
    var expenseTypeInfo: ExpenseType? = null,
    var expenseAmount: Double = 0.0,
    var expenseDescription: String = "",
    var jobCodeId: String = "",
    var jobCodeInfo: JobCode? = null,
    var costCodeId: String = "",
    var costCodeInfo: CostCode? = null,
    var expenseMileage: Double = 0.0,
    var expenseMileageRate: Double = 0.655,
    var expenseDate: String = "",
    var expenseReportRowStatus: ExpenseReportRowStatusEnum = ExpenseReportRowStatusEnum.ACTIVE,
    var expenseBy: ExpenseByEnum = ExpenseByEnum.EMPLOYEE,
    var receiptNumber: String = "",
    var createdOn: Date = Date(),
    var receiptDocumentId: String = "",
    var receiptDocumentInfo: com.syc.dashboard.query.document.entity.Document? = null,
) : TenantBaseEntity()
