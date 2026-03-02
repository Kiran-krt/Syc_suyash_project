package com.syc.dashboard.command.timesheet.api.controllers.v1

import com.syc.dashboard.command.settings.dto.SettingsResponse
import com.syc.dashboard.command.timesheet.api.commands.DeleteTimesheetRowCommand
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
class TimesheetRowDeleteController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @DeleteMapping("/api/v1/timesheet/{timesheetId}/timesheetrow/{id}/delete")
    fun deleteTimesheetRow(
        @PathVariable id: String,
        @PathVariable timesheetId: String,
        @RequestBody command: DeleteTimesheetRowCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.timesheetId = timesheetId
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            SettingsResponse(message = "TimesheetRow deleted successfully!", id = id),
            HttpStatus.OK,
        )
    }
}
