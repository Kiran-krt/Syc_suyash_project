package com.syc.dashboard.query.expensereport.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.document.dto.DocumentDto
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseByEnum
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportRowStatusEnum
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.settings.dto.ExpenseTypeDto
import java.util.*

class ExpenseReportRowDto(
    var id: String = "",
    tenantId: String = "",
    var expenseReportId: String = "",
    var expenseReportInfo: ExpenseReportDto? = null,
    var expenseTypeId: String = "",
    var expenseTypeInfo: ExpenseTypeDto? = null,
    var expenseAmount: Double = 0.0,
    var expenseDescription: String = "",
    var jobCodeId: String = "",
    var jobCodeInfo: JobCodeDto? = null,
    var costCodeId: String = "",
    var costCodeInfo: CostCodeDto? = null,
    var expenseMileage: Double = 0.0,
    var expenseMileageRate: Double = 0.655,
    var expenseDate: String = "",
    var expenseBy: ExpenseByEnum = ExpenseByEnum.EMPLOYEE,
    var expenseReportRowStatus: ExpenseReportRowStatusEnum = ExpenseReportRowStatusEnum.ACTIVE,
    var receiptNumber: String = "",
    var createdOn: Date = Date(),
    var updatedOn: Date = Date(),
    var receiptDocumentId: String = "",
    var receiptDocumentInfo: DocumentDto? = null,
) : TenantBaseDto(tenantId = tenantId)
