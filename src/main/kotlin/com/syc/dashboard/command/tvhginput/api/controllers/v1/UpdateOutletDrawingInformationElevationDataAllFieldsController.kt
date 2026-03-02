package com.syc.dashboard.command.tvhginput.api.controllers.v1

import com.syc.dashboard.command.tvhginput.api.commands.UpdateOutletDrawingInformationElevationDataAllFieldsCommand
import com.syc.dashboard.command.tvhginput.dto.TvhgInputResponse
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
@Tags(Tag(name = "V1 TVHG Input"))
@CrossOrigin
class UpdateOutletDrawingInformationElevationDataAllFieldsController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/tvhginput/{id}/outletdrawinginformation/distanceelevation/{distanceElevationId}/update/allFields")
    fun updateDistanceElevationDataAllFields(
        @PathVariable id: String,
        @PathVariable distanceElevationId: String,
        @RequestBody command: UpdateOutletDrawingInformationElevationDataAllFieldsCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id
        command.distanceElevationId = distanceElevationId

        commandDispatcher.send(command)
        return ResponseEntity(
            TvhgInputResponse(message = "Outlet drawing information distance elevation data update in tvhg input request completed successfully!", id = distanceElevationId),
            HttpStatus.CREATED,
        )
    }
}
