package com.syc.dashboard.query.timesheet.api.controllers.v1

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.ErrorDto
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetForManagerToReviewQuery
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@Tags(Tag(name = "V1 Timesheet"))
@CrossOrigin
class TimesheetFindForManagerToReviewController @Autowired constructor(
    val queryDispatcher: QueryDispatcher,
) : RootController() {

    @GetMapping("/api/v1/timesheet/pageable/lookup/submittedforapproval/formanager/manager/{id}")
    fun getTimesheetForManagerToReview(
        @PathVariable("id") id: String,
        query: FindTimesheetForManagerToReviewQuery,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Flux<out BaseDto>> {
        return try {
            query.managerId = id
            ResponseEntity.ok(queryDispatcher.send(query = query))
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to get timesheet submitted for approval for manager request!"

            log.error(safeErrorMessage)
            log.error(e.message)

            ResponseEntity.internalServerError()
                .body(
                    Flux.just(
                        ErrorDto(
                            message = safeErrorMessage,
                            errorCode = ErrorCodes.TIMESHEET_FIND_SUBMITTED_FOR_APPROVAL_FOR_MANAGER_FAILED,
                        ),
                    ),
                )
        }
    }
}
