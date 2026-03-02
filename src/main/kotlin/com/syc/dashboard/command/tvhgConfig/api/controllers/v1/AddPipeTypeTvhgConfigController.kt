package com.syc.dashboard.command.tvhgConfig.api.controllers.v1

import com.syc.dashboard.command.tvhgConfig.api.commands.AddPipeTypeTvhgConfigCommand
import com.syc.dashboard.command.tvhgConfig.dto.TvhgConfigResponse
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
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Tags(Tag(name = "V1 TVHG Config"))
@CrossOrigin
class AddPipeTypeTvhgConfigController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/tvhgconfig/add/pipetype")
    fun addPipeType(
        @RequestBody command: AddPipeTypeTvhgConfigCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = tenantId

        val pipeTypeId = UUID.randomUUID().toString()
        command.pipeTypeId = pipeTypeId

        commandDispatcher.send(command)
        return ResponseEntity(
            TvhgConfigResponse(message = "Pipe type add in tvhg config request completed successfully!", id = pipeTypeId),
            HttpStatus.CREATED,
        )
    }
}
