package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.expensereport.api.queries.SearchExpenseReportForAdminByJobCodeQuery
import com.syc.dashboard.query.expensereport.dto.ExpenseReportDto
import com.syc.dashboard.query.expensereport.dto.ExpenseReportRowDto
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportRowReactiveRepository
import com.syc.dashboard.query.jobcode.dto.JobCodeDto
import reactor.core.publisher.Flux

class SearchExpenseReportForAdminByJobCodeQueryHandler(
    private val expenseReportRowReactiveRepository: ExpenseReportRowReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as SearchExpenseReportForAdminByJobCodeQuery
        return expenseReportRowReactiveRepository
            .findByTenantIdAndJobCodeIdAndPeriodFrom(
                tenantId = query.tenantId,
                jobCodeId = query.jobCodeId,
                periodFrom = query.periodFrom,
            )
            .map {
                val expenseReportRowDto = EntityDtoConversion.toDto(it, ExpenseReportRowDto::class)
                if (it.jobCodeInfo != null) {
                    expenseReportRowDto.jobCodeInfo = EntityDtoConversion.toDto(it.jobCodeInfo!!, JobCodeDto::class)
                }

                if (it.expenseReportInfo != null) {
                    expenseReportRowDto.expenseReportInfo =
                        EntityDtoConversion.toDto(it.expenseReportInfo!!, ExpenseReportDto::class)
                }
                expenseReportRowDto
            }
    }
}
