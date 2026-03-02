package com.syc.dashboard.command.project.api.controllers.v1

import com.syc.dashboard.command.project.api.commands.AddJobCodeCommand
import com.syc.dashboard.command.project.dto.ProjectResponse
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
@Tags(Tag(name = "V1 Project"))
@CrossOrigin
class AddJobCodeController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/project/{id}/add/jobcode")
    fun addJobCode(
        @PathVariable id: String,
        @RequestBody command: AddJobCodeCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.projectId = id
        command.id = UUID.randomUUID().toString()

        commandDispatcher.send(command)
        return ResponseEntity(
            ProjectResponse(message = " Job Code created successfully!", id = command.id),
            HttpStatus.CREATED,
        )
    }
}
