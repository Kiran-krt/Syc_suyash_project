package com.syc.dashboard.command.vehiclelog.api.controllers.v1

import com.syc.dashboard.command.vehiclelog.api.commands.RegisterVehicleLogCommand
import com.syc.dashboard.command.vehiclelog.dto.VehicleLogResponse
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Tags(Tag(name = "V1 Vehicle Log"))
@CrossOrigin
class RegisterVehicleLogController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PostMapping("/api/v1/vehiclelog/register")
    fun registerVehicleLog(
        @RequestBody command: RegisterVehicleLogCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        val id = UUID.randomUUID().toString()
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            VehicleLogResponse(message = "Vehicle log registration request completed successfully!", id = id),
            HttpStatus.CREATED,
        )
    }
}
