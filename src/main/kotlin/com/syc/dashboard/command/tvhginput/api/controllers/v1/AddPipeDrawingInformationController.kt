package com.syc.dashboard.command.tvhginput.api.controllers.v1

import com.syc.dashboard.command.tvhgConfig.dto.TvhgConfigResponse
import com.syc.dashboard.command.tvhginput.api.commands.AddPipeDrawingInformationCommand
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
@Tags(Tag(name = "V1 TVHG Input"))
@CrossOrigin
class AddPipeDrawingInformationController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/tvhginput/{id}/add/pipedrawinginformation")
    fun addPipeDrawingInformation(
        @PathVariable id: String,
        @RequestBody command: AddPipeDrawingInformationCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id

        val pipeDrawingInformationId = UUID.randomUUID().toString()
        command.pipeDrawingInformationId = pipeDrawingInformationId

        commandDispatcher.send(command)
        return ResponseEntity(
            TvhgConfigResponse(message = "Pipe drawing information add in tvhg config request completed successfully!", id = pipeDrawingInformationId),
            HttpStatus.CREATED,
        )
    }
}
