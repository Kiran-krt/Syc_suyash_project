package com.syc.dashboard.query.expensereport.dto

import com.syc.dashboard.framework.core.dto.TenantBaseDto
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseReportStatusEnum
import java.util.*

class ExpenseReportDto(
    var id: String = "",
    tenantId: String = "",
    var employeeInfo: EmployeeDto? = null,
    var periodFrom: String = "",
    var periodTo: String = "",
    var employeeId: String = "",
    var supervisorId: String = "",
    var supervisorInfo: EmployeeDto? = null,
    var description: String = "",
    var status: ExpenseReportStatusEnum = ExpenseReportStatusEnum.IN_PROGRESS,
    var createdOn: Date = Date(),
    var updatedOn: Date = Date(),
    var commentsByAdmin: String = "",
    var commentsByEmployee: String = "",
    var commentsBySupervisor: String = "",
    var adminSignature: String = "",
    var employeeSignature: String = "",
    var supervisorSignature: String = "",
    var expenseReportRowsForEmployee: List<ExpenseReportRowDto>? = null,
    var expenseReportRowsForSuyash: List<ExpenseReportRowDto>? = null,
) : TenantBaseDto(tenantId = tenantId)
