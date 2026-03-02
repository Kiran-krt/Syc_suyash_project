package com.syc.dashboard.command.settings.api.controllers.v1

import com.syc.dashboard.command.settings.api.commands.RegisterVehicleInfoCommand
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
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Tags(Tag(name = "V1 Settings"))
@CrossOrigin
class RegisterVehicleInfoController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/settings/{id}/register/vehicleinfo")
    fun registerVehicle(
        @PathVariable id: String,
        @RequestBody command: RegisterVehicleInfoCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.settingsId = id
        command.id = UUID.randomUUID().toString()

        commandDispatcher.send(command)
        return ResponseEntity(
            SettingsResponse(message = "New vehicle registered successfully!", id = id),
            HttpStatus.CREATED,
        )
    }
}
