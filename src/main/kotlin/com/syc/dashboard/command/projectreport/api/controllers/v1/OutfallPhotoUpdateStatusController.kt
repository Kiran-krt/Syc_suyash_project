package com.syc.dashboard.command.projectreport.api.controllers.v1

import com.syc.dashboard.command.projectreport.api.commands.OutfallPhotoUpdateStatusCommand
import com.syc.dashboard.command.projectreport.dto.ProjectReportResponse
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
@Tags(Tag(name = "V1 Project Report"))
@CrossOrigin
class OutfallPhotoUpdateStatusController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/projectreport/{id}/outfallphoto/{outfallphotoId}/update/status")
    fun updateOutfallPhotoStatus(
        @PathVariable id: String,
        @PathVariable outfallphotoId: String,
        @RequestBody command: OutfallPhotoUpdateStatusCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id
        command.outfallPhotoId = outfallphotoId

        commandDispatcher.send(command)
        return ResponseEntity(
            ProjectReportResponse(message = "OutfallPhoto status update request completed successfully!", id = outfallphotoId),
            HttpStatus.OK,
        )
    }
}
