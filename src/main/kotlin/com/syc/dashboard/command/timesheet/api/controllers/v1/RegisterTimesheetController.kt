package com.syc.dashboard.command.timesheet.api.controllers.v1

import com.syc.dashboard.command.timesheet.api.commands.RegisterTimesheetCommand
import com.syc.dashboard.command.timesheet.dto.TimesheetResponse
import com.syc.dashboard.framework.common.exceptions.InvalidDateFormatException
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
@Tags(Tag(name = "V1 Timesheet"))
@CrossOrigin
class RegisterTimesheetController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/timesheet/register")
    fun registerTimesheet(
        @RequestBody command: RegisterTimesheetCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        return try {
            val id = UUID.randomUUID().toString()
            command.id = id

            commandDispatcher.send(command)
            ResponseEntity(
                TimesheetResponse(message = " Timesheet registration request completed successfully!", id = id),
                HttpStatus.CREATED,
            )
        } catch (exception: InvalidDateFormatException) {
            ResponseEntity(
                BaseResponse("Invalid date format"),
                HttpStatus.BAD_REQUEST,
            )
        }
    }
}
