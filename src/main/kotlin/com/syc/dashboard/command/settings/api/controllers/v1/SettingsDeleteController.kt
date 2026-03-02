package com.syc.dashboard.command.settings.api.controllers.v1

import com.syc.dashboard.command.settings.api.commands.DeleteSettingsCommand
import com.syc.dashboard.command.settings.dto.SettingsResponse
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
@Tags(Tag(name = "V1 Settings"))
@CrossOrigin
class SettingsDeleteController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @DeleteMapping("/api/v1/settings/{id}/delete")
    fun deleteSettings(
        @RequestBody command: DeleteSettingsCommand,
        @PathVariable id: String,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            SettingsResponse(message = "Settings deleted successfully!", id = id),
            HttpStatus.OK,
        )
    }
}
