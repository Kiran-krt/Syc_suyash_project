package com.syc.dashboard.query.timesheet.api.controllers.v1

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.ErrorDto
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.timesheet.api.queries.SearchTimesheetForManagerQuery
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@Tags(Tag(name = "V1 Timesheet"))
@CrossOrigin
class SearchTimesheetForManagerController @Autowired constructor(
    val queryDispatcher: QueryDispatcher,
) : RootController() {

    @GetMapping("/api/v1/timesheet/lookup/search/formanager/{id}")
    fun getTimesheetForManager(
        @PathVariable("id") id: String,
        query: SearchTimesheetForManagerQuery,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Mono<out BaseDto>> {
        return try {
            query.managerId = id
            ResponseEntity.ok(
                queryDispatcher.send(query = query).toMono(),
            )
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to get Timesheet by manager id request!"

            log.error(safeErrorMessage)
            log.error(e.message)

            ResponseEntity.internalServerError()
                .body(
                    Mono.just(
                        ErrorDto(
                            message = safeErrorMessage,
                            errorCode = ErrorCodes.TIMESHEET_SEARCH_BY_MANAGER_FAILED,
                        ),
                    ),
                )
        }
    }
}
