package com.syc.dashboard.command.projectreport.api.controllers.v1

import com.syc.dashboard.command.projectreport.api.commands.AppendixUpdateAllFieldsCommand
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
class AppendixUpdateAllFieldsController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/projectreport/{id}/appendix/{appendixId}/update/allfields")
    fun updateAllFields(
        @PathVariable id: String,
        @PathVariable appendixId: String,
        @RequestBody command: AppendixUpdateAllFieldsCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id
        command.appendixId = appendixId

        commandDispatcher.send(command)
        return ResponseEntity(
            ProjectReportResponse(message = "Appendix all fields update request completed successfully!", id = appendixId),
            HttpStatus.OK,
        )
    }
}
