package com.syc.dashboard.command.employee.api.controllers.v1

import com.syc.dashboard.command.employee.api.commands.RegisterEmployeeCommand
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
import java.util.*

@RestController
@Tags(Tag(name = "V1 Employee"))
@CrossOrigin
class RegisterEmployeeController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/employee/register")
    fun registerEmployee(
        @RequestBody command: RegisterEmployeeCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        val id = UUID.randomUUID().toString()
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            EmployeeResponse(message = " Employee registration request completed successfully!", id = id),
            HttpStatus.CREATED,
        )
    }
}
