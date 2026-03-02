package com.syc.dashboard.command.admin.api.controllers.v1

import com.syc.dashboard.command.admin.api.commands.AdminUpdateMobileDeviceInfoCommand
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
@Tags(Tag(name = "V1 Admin"))
@CrossOrigin
class AdminUpdateMobileDeviceInfoController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/admin/{id}/update/mobiledeviceinfo")
    fun updateMobileDeviceInfo(
        @PathVariable("id") id: String,
        @RequestBody command: AdminUpdateMobileDeviceInfoCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id

        commandDispatcher.send(command)
        log.info("Mobile device info updated for admin with id '${command.id}'.")
        return ResponseEntity(BaseResponse("Mobile device info updated successfully!"), HttpStatus.OK)
    }
}
