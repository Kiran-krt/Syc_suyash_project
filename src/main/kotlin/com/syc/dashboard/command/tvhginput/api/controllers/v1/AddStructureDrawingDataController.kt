package com.syc.dashboard.command.tvhginput.api.controllers.v1

import com.syc.dashboard.command.tvhginput.api.commands.AddStructureDrawingDataCommand
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
class AddStructureDrawingDataController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/tvhginput/{id}/add/structuredrawingdata")
    fun addStructureDrawingData(
        @PathVariable id: String,
        @RequestBody command: AddStructureDrawingDataCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id
        command.structureDrawingDataId = UUID.randomUUID().toString()

        commandDispatcher.send(command)
        return ResponseEntity(
            TvhgInputResponse(message = "Structure drawing data created successfully!", id = command.structureDrawingDataId),
            HttpStatus.CREATED,
        )
    }
}
