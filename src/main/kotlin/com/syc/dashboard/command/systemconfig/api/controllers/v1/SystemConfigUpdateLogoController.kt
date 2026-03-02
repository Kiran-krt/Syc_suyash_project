package com.syc.dashboard.command.systemconfig.api.controllers.v1

import com.syc.dashboard.command.systemconfig.api.commands.SystemConfigUpdateLogoCommand
import com.syc.dashboard.command.systemconfig.dto.SystemConfigResponse
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tags(Tag(name = "V1 System Config"))
@CrossOrigin
class SystemConfigUpdateLogoController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/systemconfig/update/logo")
    fun updateLogo(
        @RequestBody command: SystemConfigUpdateLogoCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = tenantId

        commandDispatcher.send(command)
        return ResponseEntity(
            SystemConfigResponse(message = "System Config logo update request completed successfully!", id = tenantId),
            HttpStatus.OK,
        )
    }
}
