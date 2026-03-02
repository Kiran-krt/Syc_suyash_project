package com.syc.dashboard.command.employee.api.controllers.v1

import com.syc.dashboard.command.employee.api.commands.EmployeeResetPasswordCommand
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
class EmployeeResetPasswordController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/employee/reset/password/byemail")
    fun resetPasswordForEmployee(
        @RequestBody command: EmployeeResetPasswordCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        commandDispatcher.send(command)
        return ResponseEntity(
            BaseResponse(message = "Employee reset password request completed successfully!"),
            HttpStatus.OK,
        )
    }
}
