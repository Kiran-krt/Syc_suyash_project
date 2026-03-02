package com.syc.dashboard.command.projectreport.api.controllers.v1

import com.syc.dashboard.command.projectreport.api.commands.OutfallPhotoUploadCommand
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
import java.util.*

@RestController
@Tags(Tag(name = "V1 Project Report"))
@CrossOrigin
class OutfallPhotoUploadController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/projectreport/{projectReportId}/upload/outfallphoto")
    fun uploadOutfallPhoto(
        @PathVariable projectReportId: String,
        @RequestBody command: OutfallPhotoUploadCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = projectReportId
        command.outfallPhotoId = UUID.randomUUID().toString()
        commandDispatcher.send(command)
        return ResponseEntity(
            ProjectReportResponse(message = " Outfall photo Upload successfully!", id = projectReportId),
            HttpStatus.CREATED,
        )
    }
}
