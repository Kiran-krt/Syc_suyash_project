package com.syc.dashboard.query.vehiclelog.api.controllers.v1

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.ErrorDto
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.vehiclelog.api.queries.FindVehicleLogByTenantIdQuery
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
@Tags(Tag(name = "V1 Vehicle Log"))
@CrossOrigin
class FindVehicleLogByTenantIdController @Autowired constructor(
    val queryDispatcher: QueryDispatcher,
) : RootController() {

    @GetMapping("/api/v1/vehiclelog/lookup/all")
    fun getVehicleLogByTenantId(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Flux<out BaseDto>> {
        val query = buildQuery(FindVehicleLogByTenantIdQuery())

        return try {
            ResponseEntity.ok(
                queryDispatcher.send(query = query),
            )
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to get vehicle log by tenant id request!"

            log.error(safeErrorMessage)
            log.error(e.message)

            ResponseEntity.internalServerError()
                .body(
                    Flux.just(
                        ErrorDto(
                            message = safeErrorMessage,
                            errorCode = ErrorCodes.VEHICLE_LOG_FIND_BY_TENANT_ID_FAILED,
                        ),
                    ),
                )
        }
    }
}
