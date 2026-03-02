package com.syc.dashboard.command.jobcode.api.controllers.v1

import com.syc.dashboard.command.jobcode.api.commands.UpdateCostCodeCommand
import com.syc.dashboard.command.jobcode.dto.JobCodeResponse
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
@Tags(Tag(name = "V1 JobCode"))
@CrossOrigin
class UpdateCostCodeController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/jobcode/{jobCodeId}/costcode/{id}/update")
    fun updateCostCode(
        @PathVariable jobCodeId: String,
        @PathVariable id: String,
        @RequestBody command: UpdateCostCodeCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id
        command.jobCodeId = jobCodeId

        commandDispatcher.send(command)
        return ResponseEntity(
            JobCodeResponse(message = "Cost code update request completed successfully!", id = id),
            HttpStatus.OK,
        )
    }
}
