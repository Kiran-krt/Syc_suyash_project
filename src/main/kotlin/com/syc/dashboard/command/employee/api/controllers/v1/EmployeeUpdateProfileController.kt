package com.syc.dashboard.command.employee.api.controllers.v1

import com.syc.dashboard.command.employee.api.commands.EmployeeUpdateProfileCommand
import com.syc.dashboard.command.employee.dto.EmployeeResponse
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
@Tags(Tag(name = "V1 Employee"))
@CrossOrigin
class EmployeeUpdateProfileController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/employee/{id}/update/profile")
    fun updateEmployeeProfile(
        @PathVariable id: String,
        @RequestBody command: EmployeeUpdateProfileCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,

    ): ResponseEntity<BaseResponse> {
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            EmployeeResponse(message = "Employee profile update request completed successfully!", id = id),
            HttpStatus.OK,
        )
    }
}
