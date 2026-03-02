package com.syc.dashboard.command.projectreport.api.controllers.v1

import com.syc.dashboard.command.projectreport.api.commands.RegisterProjectReportCommand
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
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Tags(Tag(name = "V1 Project Report"))
@CrossOrigin
class RegisterProjectReportController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/projectreport/register")
    fun registerProjectReport(
        @RequestBody command: RegisterProjectReportCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        val id = UUID.randomUUID().toString()
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            ProjectReportResponse(message = " Project report added successfully!", id = id),
            HttpStatus.CREATED,
        )
    }
}
