package com.syc.dashboard.query.expensereport.api.controllers.v1

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.ErrorDto
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.expensereport.api.queries.ExpenseReportSearchRowExportToExcelQuery
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@Tags(Tag(name = "V1 Expense Report"))
@CrossOrigin
class ExpenseReportSearchRowExportToExcelController @Autowired constructor(
    val queryDispatcher: QueryDispatcher,
) : RootController() {

    @GetMapping("/api/v1/expensereport/jobcode/periodfrom/export/toexcel")
    fun getExpenseReportSearchRows(
        query: ExpenseReportSearchRowExportToExcelQuery,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Mono<out BaseDto>> {
        return try {
            ResponseEntity.ok(queryDispatcher.send(query = query).toMono())
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to export expense report row to excel by id request!"

            log.error(safeErrorMessage)
            log.error(e.message)

            ResponseEntity.internalServerError()
                .body(
                    Mono.just(
                        ErrorDto(
                            message = safeErrorMessage,
                            errorCode = ErrorCodes.EXPENSE_REPORT_ROW_EXPORT_TO_EXCEL_BY_ID_FAILED,
                        ),
                    ),
                )
        }
    }
}
