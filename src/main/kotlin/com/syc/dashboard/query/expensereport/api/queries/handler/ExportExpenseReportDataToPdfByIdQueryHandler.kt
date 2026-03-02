package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.document.dto.DocumentDto
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.expensereport.api.queries.ExportExpenseReportDataToPdfByIdQuery
import com.syc.dashboard.query.expensereport.dto.ExpenseReportDto
import com.syc.dashboard.query.expensereport.dto.ExpenseReportRowDto
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportReactiveRepository
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.settings.dto.ExpenseTypeDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class ExportExpenseReportDataToPdfByIdQueryHandler(
    private val expenseReportReactiveRepository: ExpenseReportReactiveRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as ExportExpenseReportDataToPdfByIdQuery
        return expenseReportReactiveRepository
            .findByTenantIdAndIdWithAllDetails(
                tenantId = query.tenantId,
                id = query.expenseReportId,
            )
            .flatMap { expenseReport ->
                if (expenseReport.supervisorInfo == null) {
                    adminReactiveRepository.findByTenantIdAndId(
                        expenseReport.tenantId,
                        expenseReport.supervisorId,
                    )
                        .map {
                            expenseReport.supervisorInfo = it
                            expenseReport
                        }
                        .switchIfEmpty { Mono.just(expenseReport) }
                } else {
                    Mono.just(expenseReport)
                }
            }
            .map { expenseReport ->
                val expenseReportDto = EntityDtoConversion.toDto(expenseReport, ExpenseReportDto::class)

                if (expenseReport.employeeInfo != null) {
                    expenseReportDto.employeeInfo =
                        EntityDtoConversion.toDto(expenseReport.employeeInfo!!, EmployeeDto::class)
                }

                if (expenseReport.supervisorInfo != null) {
                    expenseReportDto.supervisorInfo =
                        EntityDtoConversion.toDto(expenseReport.supervisorInfo!!, EmployeeDto::class)
                }
                if (expenseReport.expenseReportRowsForEmployee != null) {
                    expenseReportDto.expenseReportRowsForEmployee = expenseReport.expenseReportRowsForEmployee?.map {
                        val expenseReportRowDto = EntityDtoConversion.toDto(it, ExpenseReportRowDto::class)

                        if (it.expenseTypeInfo != null) {
                            expenseReportRowDto.expenseTypeInfo =
                                EntityDtoConversion.toDto(it.expenseTypeInfo!!, ExpenseTypeDto::class)
                        }
                        if (it.jobCodeInfo != null) {
                            expenseReportRowDto.jobCodeInfo =
                                EntityDtoConversion.toDto(it.jobCodeInfo!!, JobCodeDto::class)
                        }
                        if (it.costCodeInfo != null) {
                            expenseReportRowDto.costCodeInfo =
                                EntityDtoConversion.toDto(it.costCodeInfo!!, CostCodeDto::class)
                        }
                        if (it.receiptDocumentInfo != null) {
                            expenseReportRowDto.receiptDocumentInfo =
                                EntityDtoConversion.toDto(it.receiptDocumentInfo!!, DocumentDto::class)
                        }
                        expenseReportRowDto
                    }
                }

                if (expenseReport.expenseReportRowsForSuyash != null) {
                    expenseReportDto.expenseReportRowsForSuyash = expenseReport.expenseReportRowsForSuyash?.map {
                        val expenseReportRowDto = EntityDtoConversion.toDto(it, ExpenseReportRowDto::class)

                        if (it.expenseTypeInfo != null) {
                            expenseReportRowDto.expenseTypeInfo =
                                EntityDtoConversion.toDto(it.expenseTypeInfo!!, ExpenseTypeDto::class)
                        }
                        if (it.jobCodeInfo != null) {
                            expenseReportRowDto.jobCodeInfo =
                                EntityDtoConversion.toDto(it.jobCodeInfo!!, JobCodeDto::class)
                        }
                        if (it.costCodeInfo != null) {
                            expenseReportRowDto.costCodeInfo =
                                EntityDtoConversion.toDto(it.costCodeInfo!!, CostCodeDto::class)
                        }
                        if (it.receiptDocumentInfo != null) {
                            expenseReportRowDto.receiptDocumentInfo =
                                EntityDtoConversion.toDto(it.receiptDocumentInfo!!, DocumentDto::class)
                        }
                        expenseReportRowDto
                    }
                }
                expenseReportDto
            }
            .flux()
    }
}
