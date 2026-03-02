package com.syc.dashboard.command.settings.api.controllers.v1

import com.syc.dashboard.command.settings.api.commands.VehicleInfoUpdateAllFieldsCommand
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
class VehicleInfoUpdateAllFieldsController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/settings/{settingsId}/vehicleinfo/{id}/update/allfields")
    fun updateVehicleInfoAllFields(
        @PathVariable settingsId: String,
        @PathVariable id: String,
        @RequestBody command: VehicleInfoUpdateAllFieldsCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.settingsId = settingsId
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            SettingsResponse(message = "Vehicle info all fields updated successfully!", id = id),
            HttpStatus.OK,
        )
    }
}
