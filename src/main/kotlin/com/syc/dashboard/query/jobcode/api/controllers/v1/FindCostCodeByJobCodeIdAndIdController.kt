package com.syc.dashboard.query.jobcode.api.controllers.v1

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.ErrorDto
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.jobcode.api.queries.FindCostCodByJobCodeIdIdAndIdQuery
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
@Tags(Tag(name = "V1 JobCode"))
@CrossOrigin
class FindCostCodeByJobCodeIdAndIdController @Autowired constructor(
    val queryDispatcher: QueryDispatcher,
) : RootController() {

    @GetMapping("/api/v1/jobcode/{jobCodeId}/costcode/{id}/lookup")
    fun getCostCodeById(
        @PathVariable jobCodeId: String,
        @PathVariable id: String,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Mono<out BaseDto>> {
        val query = buildQuery(FindCostCodByJobCodeIdIdAndIdQuery(jobCodeId = jobCodeId, id = id))

        return try {
            ResponseEntity.ok(
                queryDispatcher.send(query = query).toMono(),
            )
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to get cost code by job code id and id request!"

            log.error(safeErrorMessage)
            log.error(e.message)

            ResponseEntity.internalServerError()
                .body(
                    Mono.just(
                        ErrorDto(
                            message = safeErrorMessage,
                            errorCode = ErrorCodes.COSTCODE_FIND_BY_JOB_CODE_ID_AND_ID_FAILED,
                        ),
                    ),
                )
        }
    }
}
