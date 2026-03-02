package com.syc.dashboard.query.expensereport.api.queries

import com.syc.dashboard.framework.common.dto.CountDto
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.admin.repository.jpa.AdminRepository
import com.syc.dashboard.query.admin.repository.reactive.AdminReactiveRepository
import com.syc.dashboard.query.expensereport.api.queries.handler.*
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportReactiveRepository
import com.syc.dashboard.query.expensereport.repository.reactive.ExpenseReportRowReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Service
class ExpenseReportQueryHandler @Autowired constructor(
    private val expenseReportReactiveRepository: ExpenseReportReactiveRepository,
    private val expenseReportRowReactiveRepository: ExpenseReportRowReactiveRepository,
    private val adminRepository: AdminRepository,
    private val adminReactiveRepository: AdminReactiveRepository,
) : QueryHandler {

    private fun handle(query: FindExpenseReportByIdQuery): Flux<out BaseDto> {
        return FindExpenseReportByIdQueryHandler(
            expenseReportReactiveRepository = expenseReportReactiveRepository,
            adminRepository = adminRepository,
        ).handle(query)
    }

    private fun handle(query: PageableSearchExpenseReportByFilterQuery): Flux<out BaseDto> {
        return PageableSearchExpenseReportByFilterQueryHandler(
            expenseReportReactiveRepository = expenseReportReactiveRepository,
            adminRepository = adminRepository,
        ).handle(query)
    }

    private fun handle(query: FindExpenseReportCountForAdminByStatusQuery): Flux<out BaseDto> {
        return FindExpenseReportCountForAdminByStatusQueryHandler(
            expenseReportReactiveRepository = expenseReportReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindExpenseReportCountForEmployeeByStatusQuery): Flux<out BaseDto> {
        return FindExpenseReportCountForEmployeeByStatusQueryHandler(
            expenseReportReactiveRepository = expenseReportReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindExpenseReportCountForSupervisorByStatusQuery): Flux<out BaseDto> {
        return FindExpenseReportCountForSupervisorByStatusQueryHandler(
            expenseReportReactiveRepository = expenseReportReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindExpenseRowsForSuyashByIdQuery): Flux<out BaseDto> {
        return FindExpenseRowsForSuyashByIdQueryHandler(
            expenseReportRowReactiveRepository = expenseReportRowReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindExpenseRowsForEmployeeByIdQuery): Flux<out BaseDto> {
        return FindExpenseRowsForEmployeeByIdQueryHandler(
            expenseReportRowReactiveRepository = expenseReportRowReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: ExportExpenseReportToExcelByIdQuery): Flux<out BaseDto> {
        return ExportExpenseReportToExelByIdQueryHandler(
            expenseReportReactiveRepository = expenseReportReactiveRepository,
            adminReactiveRepository = adminReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: ExportExpenseReportDataToPdfByIdQuery): Flux<out BaseDto> {
        return ExportExpenseReportDataToPdfByIdQueryHandler(
            expenseReportReactiveRepository = expenseReportReactiveRepository,
            adminReactiveRepository = adminReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: SearchExpenseReportForAdminByJobCodeQuery): Flux<out BaseDto> {
        return SearchExpenseReportForAdminByJobCodeQueryHandler(
            expenseReportRowReactiveRepository = expenseReportRowReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: SearchExpenseReportByJobCodeAndPeriodFromQuery): Flux<out BaseDto> {
        return SearchExpenseReportByJobCodeAndPeriodFromQueryHandler(
            expenseReportRowReactiveRepository = expenseReportRowReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: ExpenseReportSearchRowExportToExcelQuery): Flux<out BaseDto> {
        return ExpenseReportSearchRowExportToExcelQueryHandler(
            expenseReportRowReactiveRepository = expenseReportRowReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindJobCodeByPeriodFromQuery): Flux<out BaseDto> {
        return FindJobCodeByPeriodFormQueryHandler(
            expenseReportRowReactiveRepository = expenseReportRowReactiveRepository,
        ).handle(query)
    }

    private fun handle(query: FindExpenseReportCountForManagerByStatusQuery): Flux<out BaseDto> {
        return expenseReportReactiveRepository
            .expenseReportCountByTenantIdAndApprovedByUserIdAndStatus(
                tenantId = query.tenantId,
                status = query.statusList,
            )
            .map { CountDto(it) }
            .toFlux()
    }

    private fun handle(query: FindExpenseReportByTenantIdQuery): Flux<out BaseDto> {
        return FindExpenseReportByTenantIdQueryHandler(
            expenseReportReactiveRepository = expenseReportReactiveRepository,
        ).handle(query)
    }

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        return when (query) {
            is FindExpenseReportByIdQuery -> handle(query)
            is PageableSearchExpenseReportByFilterQuery -> handle(query)
            is FindExpenseReportCountForAdminByStatusQuery -> handle(query)
            is FindExpenseReportCountForEmployeeByStatusQuery -> handle(query)
            is FindExpenseReportCountForSupervisorByStatusQuery -> handle(query)
            is FindExpenseRowsForSuyashByIdQuery -> handle(query)
            is FindExpenseRowsForEmployeeByIdQuery -> handle(query)
            is ExportExpenseReportToExcelByIdQuery -> handle(query)
            is ExportExpenseReportDataToPdfByIdQuery -> handle(query)
            is SearchExpenseReportForAdminByJobCodeQuery -> handle(query)
            is SearchExpenseReportByJobCodeAndPeriodFromQuery -> handle(query)
            is ExpenseReportSearchRowExportToExcelQuery -> handle(query)
            is FindJobCodeByPeriodFromQuery -> handle(query)
            is FindExpenseReportCountForManagerByStatusQuery -> handle(query)
            is FindExpenseReportByTenantIdQuery -> handle(query)
            else -> Flux.empty()
        }
    }
}
