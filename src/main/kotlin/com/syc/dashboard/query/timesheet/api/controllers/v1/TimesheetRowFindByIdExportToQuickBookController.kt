package com.syc.dashboard.query.timesheet.api.controllers.v1

import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.query.timesheet.api.queries.FindTimesheetRowByIdExportToQuickBookQuery
import com.syc.dashboard.query.timesheet.api.queries.handler.FindTimesheetRowByIdExportToQuickBookQueryHandler
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@Tags(Tag(name = "V1 Timesheet"))
@CrossOrigin
class TimesheetRowFindByIdExportToQuickBookController @Autowired constructor(
    private val findTimesheetRowByIdExportToQuickBookQueryHandler: FindTimesheetRowByIdExportToQuickBookQueryHandler,
) : RootController() {

    @GetMapping("/api/v1/timesheet/{id}/export/toquickbook")
    fun getTimesheetByIdExportToExcel(
        @PathVariable("id") id: String,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): Mono<ResponseEntity<ByteArray>> {
        val query = FindTimesheetRowByIdExportToQuickBookQuery(tenantId = tenantId, timesheetId = id)

        return findTimesheetRowByIdExportToQuickBookQueryHandler
            .handleAndGenerateIIFFile(query)
            .map { iifFile ->
                ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"timesheet.iif\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(iifFile)
            }
            .onErrorResume { e ->
                val safeErrorMessage = "Failed to get timesheet row by id export to QuickBook request!"
                log.error(safeErrorMessage)
                log.error(e.message)

                Mono.just(
                    ResponseEntity
                        .internalServerError()
                        .body("Failed to generate IIF file: $safeErrorMessage".toByteArray()),
                )
            }
    }
}
