package com.syc.dashboard.query.expensereport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.framework.core.utils.EntityDtoConversion
import com.syc.dashboard.query.admin.repository.jpa.AdminRepository
import com.syc.dashboard.query.employee.dto.EmployeeDto
import com.syc.dashboard.query.expensereport.api.queries.PageableSearchExpenseReportByFilterQuery
import com.syc.dashboard.query.expensereport.dto.ExpenseReportDto
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportReactiveRepository
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

class PageableSearchExpenseReportByFilterQueryHandler(
    private val expenseReportReactiveRepository: ExpenseReportReactiveRepository,
    private val adminRepository: AdminRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as PageableSearchExpenseReportByFilterQuery
        return expenseReportReactiveRepository
            .findByTenantIdAndStatusAndEmployeeIdAndSupervisorIdAndDescription(
                tenantId = query.tenantId,
                status = query.status,
                employeeId = query.employeeId,
                supervisorId = query.supervisorId,
                description = query.description,
                pageable = PageRequest.of(
                    query.page,
                    query.limit,
                    query.sort,
                    "periodFrom_toDate",
                ),
            )
            .publishOn(Schedulers.boundedElastic())
            .map { expenseReport ->
                if (expenseReport.supervisorInfo == null) {
                    expenseReport.supervisorInfo = adminRepository.findByTenantIdAndId(
                        tenantId = expenseReport.tenantId,
                        id = expenseReport.supervisorId,
                    )
                }
                expenseReport
            }
            .map {
                val expenseReportDto = EntityDtoConversion.toDto(it, ExpenseReportDto::class)
                if (it.employeeInfo != null) {
                    expenseReportDto.employeeInfo = EntityDtoConversion.toDto(it.employeeInfo!!, EmployeeDto::class)
                }
                if (it.supervisorInfo != null) {
                    expenseReportDto.supervisorInfo = EntityDtoConversion.toDto(it.supervisorInfo!!, EmployeeDto::class)
                }
                expenseReportDto
            }
    }
}
