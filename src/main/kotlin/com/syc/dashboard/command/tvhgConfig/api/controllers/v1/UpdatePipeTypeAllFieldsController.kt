package com.syc.dashboard.command.tvhgConfig.api.controllers.v1

import com.syc.dashboard.command.tvhgConfig.api.commands.UpdatePipeTypeAllFieldsCommand
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

@RestController
@Tags(Tag(name = "V1 TVHG Config"))
@CrossOrigin
class UpdatePipeTypeAllFieldsController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/tvhgconfig/pipeType/{pipeTypeId}/update/allfields")
    fun updatePipeTypeAllFields(
        @PathVariable pipeTypeId: String,
        @RequestBody command: UpdatePipeTypeAllFieldsCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = tenantId
        command.pipeTypeId = pipeTypeId

        commandDispatcher.send(command)
        return ResponseEntity(
            TvhgConfigResponse(message = "PipeType update in tvhgConfig request completed successfully!", id = command.pipeTypeId),
            HttpStatus.CREATED,
        )
    }
}
