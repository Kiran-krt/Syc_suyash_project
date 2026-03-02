package com.syc.dashboard.query.employee.api.controllers.v1

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.ErrorDto
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.employee.api.queries.SearchAllSupervisorListQuery
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@Tags(Tag(name = "V1 Employee"))
@CrossOrigin
class SearchAllSupervisorListController @Autowired constructor(
    val queryDispatcher: QueryDispatcher,
) : RootController() {

    @GetMapping("/api/v1/employee/lookup/all/supervisor")
    fun getAllSupervisorList(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Flux<out BaseDto>> {
        val query = buildQuery(SearchAllSupervisorListQuery())

        return try {
            ResponseEntity.ok(queryDispatcher.send(query = query))
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to get supervisorlist by filter request!"

            log.error(safeErrorMessage)
            log.error(e.message)

            ResponseEntity.internalServerError()
                .body(
                    Flux.just(
                        ErrorDto(
                            message = safeErrorMessage,
                            errorCode = ErrorCodes.EMPLOYEE_SUPERVISOR_LIST_FIND_BY_FILTER_FAILED,
                        ),
                    ),
                )
        }
    }
}
