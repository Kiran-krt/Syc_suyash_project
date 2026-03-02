package com.syc.dashboard.query.systemconfig.api.controllers.v1

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.dto.ErrorDto
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.systemconfig.api.query.FindSystemConfigQuery
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

@RestController
@Tags(Tag(name = "V1 System Config"))
@CrossOrigin
class FindSystemConfigController @Autowired constructor(
    val queryDispatcher: QueryDispatcher,
) : RootController() {

    @GetMapping("/api/v1/systemconfig/lookup")
    fun getSystemConfig(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Mono<out BaseDto>> {
        val query = buildQuery(FindSystemConfigQuery())

        return try {
            ResponseEntity.ok(queryDispatcher.send(query = query).single())
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to get system config for tenant '${query.tenantId}'!"

            log.error(safeErrorMessage)
            log.error(e.message)

            ResponseEntity.internalServerError()
                .body(
                    Mono.just(
                        ErrorDto(
                            message = safeErrorMessage,
                            errorCode = ErrorCodes.SYSTEM_CONFIG_FIND_FAILED,
                        ),
                    ),
                )
        }
    }
}
