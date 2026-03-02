package com.syc.dashboard.command.tvhginput.api.controllers.v1

import com.syc.dashboard.command.tvhginput.api.commands.UpdateFlowPathDrawingInformationAllFieldsCommand
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
class UpdateFlowPathDrawingInformationAllFieldController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/tvhginput/{id}/flowpathdrawinginformation/{flowPathDrawingInformationId}/update/allfields")
    fun updateFlowPathDrawingInformation(
        @PathVariable id: String,
        @PathVariable flowPathDrawingInformationId: String,
        @RequestBody command: UpdateFlowPathDrawingInformationAllFieldsCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id
        command.flowPathDrawingInformationId = flowPathDrawingInformationId

        commandDispatcher.send(command)
        return ResponseEntity(
            TvhgInputResponse(message = "Flow path drawing information updated successfully!", id = flowPathDrawingInformationId),
            HttpStatus.OK,
        )
    }
}
