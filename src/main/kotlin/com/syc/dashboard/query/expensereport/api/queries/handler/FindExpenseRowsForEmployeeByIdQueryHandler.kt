package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.document.dto.DocumentDto
import com.syc.dashboard.query.expensereport.api.queries.FindExpenseRowsForEmployeeByIdQuery
import com.syc.dashboard.query.expensereport.dto.ExpenseReportRowDto
import com.syc.dashboard.query.expensereport.entity.enums.ExpenseByEnum
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportRowReactiveRepository
import com.syc.dashboard.query.jobcode.dto.CostCodeDto
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import com.syc.dashboard.query.settings.dto.ExpenseTypeDto
import reactor.core.publisher.Flux

class FindExpenseRowsForEmployeeByIdQueryHandler(
    private val expenseReportRowReactiveRepository: ExpenseReportRowReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindExpenseRowsForEmployeeByIdQuery
        return expenseReportRowReactiveRepository
            .findByTenantIdAndExpenseReportIdAndExpenseBy(
                tenantId = query.tenantId,
                expenseReportId = query.expenseReportId,
                expenseBy = ExpenseByEnum.EMPLOYEE,
            )
            .map {
                val expenseReportRowDto = EntityDtoConversion.toDto(it, ExpenseReportRowDto::class)
                if (it.expenseTypeInfo != null) {
                    expenseReportRowDto.expenseTypeInfo =
                        EntityDtoConversion.toDto(it.expenseTypeInfo!!, ExpenseTypeDto::class)
                }
                if (it.jobCodeInfo != null) {
                    expenseReportRowDto.jobCodeInfo = EntityDtoConversion.toDto(it.jobCodeInfo!!, JobCodeDto::class)
                }
                if (it.costCodeInfo != null) {
                    expenseReportRowDto.costCodeInfo = EntityDtoConversion.toDto(it.costCodeInfo!!, CostCodeDto::class)
                }
                if (it.receiptDocumentInfo != null) {
                    expenseReportRowDto.receiptDocumentInfo =
                        EntityDtoConversion.toDto(it.receiptDocumentInfo!!, DocumentDto::class)
                }
                expenseReportRowDto
            }
    }
}
