package com.syc.dashboard.command.timesheet.api.controllers.v1

import com.syc.dashboard.command.timesheet.api.commands.TimesheetUpdateApproverCommand
import com.syc.dashboard.command.timesheet.dto.TimesheetResponse
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
@Tags(Tag(name = "V1 Timesheet"))
@CrossOrigin
class TimesheetUpdateApproverController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/timesheet/{id}/update/approver")
    fun updateTimesheetApprover(
        @PathVariable id: String,
        @RequestBody command: TimesheetUpdateApproverCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            TimesheetResponse(message = " Timesheet approver update request completed successfully!", id = id),
            HttpStatus.OK,
        )
    }
}
