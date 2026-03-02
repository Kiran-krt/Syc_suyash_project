package com.syc.dashboard.command.tvhginput.api.controllers.v1

import com.syc.dashboard.command.tvhginput.api.commands.UpdatePipeInformationAllFieldCommand
import com.syc.dashboard.command.tvhginput.dto.TvhgInputResponse
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

@RestController
@Tags(Tag(name = "V1 TVHG Input"))
@CrossOrigin
class UpdatePipeInformationAllFieldController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/tvhginput/{id}/pipeinformation/{pipeInformationId}/update/allfields")
    fun updatePipeInformationAllField(
        @PathVariable id: String,
        @PathVariable pipeInformationId: String,
        @RequestBody command: UpdatePipeInformationAllFieldCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id
        command.pipeInformationId = pipeInformationId

        commandDispatcher.send(command)
        return ResponseEntity(
            TvhgInputResponse(message = "Pipe Information update in tvhg input request completed successfully!", id = command.pipeInformationId),
            HttpStatus.CREATED,
        )
    }
}
