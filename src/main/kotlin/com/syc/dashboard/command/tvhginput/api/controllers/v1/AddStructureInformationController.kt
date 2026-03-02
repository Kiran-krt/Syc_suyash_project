package com.syc.dashboard.command.tvhginput.api.controllers.v1

import com.syc.dashboard.command.tvhginput.api.commands.AddStructureInformationCommand
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
import java.util.*

@RestController
@Tags(Tag(name = "V1 TVHG Input"))
@CrossOrigin
class AddStructureInformationController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/tvhginput/{id}/add/structureinformation")
    fun addStructureInformation(
        @PathVariable id: String,
        @RequestBody command: AddStructureInformationCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id
        command.structureInformationId = UUID.randomUUID().toString()

        commandDispatcher.send(command)
        return ResponseEntity(
            TvhgInputResponse(message = "Structure information created successfully!", id = command.structureInformationId),
            HttpStatus.CREATED,
        )
    }
}
