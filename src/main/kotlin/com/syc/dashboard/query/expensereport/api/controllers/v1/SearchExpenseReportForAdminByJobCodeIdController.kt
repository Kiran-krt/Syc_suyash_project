package com.syc.dashboard.query.expensereport.api.controllers.v1

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.ErrorDto
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.expensereport.api.queries.SearchExpenseReportForAdminByJobCodeQuery
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@Tags(Tag(name = "V1 Expense Report"))
@CrossOrigin
class SearchExpenseReportForAdminByJobCodeIdController @Autowired constructor(
    val queryDispatcher: QueryDispatcher,
) : RootController() {

    @GetMapping("/api/v1/expensereport/lookup/search/forjobcode/{id}")
    fun getExpenseReportForAdmin(
        @PathVariable("id") id: String,
        query: SearchExpenseReportForAdminByJobCodeQuery,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Flux<out BaseDto>> {
        return try {
            query.jobCodeId = id
            ResponseEntity.ok(
                queryDispatcher.send(buildQuery(query = query)),
            )
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to get expense report by jobcode & period From request!"

            log.error(safeErrorMessage)
            log.error(e.message)

            ResponseEntity.internalServerError()
                .body(
                    Flux.just(
                        ErrorDto(
                            message = safeErrorMessage,
                            errorCode = ErrorCodes.EXPENSE_REPORT_SEARCH_BY_ADMIN_FAILED,
                        ),
                    ),
                )
        }
    }
}
