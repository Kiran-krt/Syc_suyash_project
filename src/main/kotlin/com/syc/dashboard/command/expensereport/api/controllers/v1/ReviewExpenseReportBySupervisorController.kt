package com.syc.dashboard.command.expensereport.api.controllers.v1

import com.syc.dashboard.command.expensereport.api.commands.ReviewExpenseReportBySupervisorCommand
import com.syc.dashboard.command.expensereport.dto.ExpenseReportResponse
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
@Tags(Tag(name = "V1 Expense Report"))
@CrossOrigin
class ReviewExpenseReportBySupervisorController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @PutMapping("/api/v1/expensereport/{id}/review/bysupervisor")
    fun reviewExpenseReportBySupervisor(
        @PathVariable id: String,
        @RequestBody command: ReviewExpenseReportBySupervisorCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.id = id

        commandDispatcher.send(command)
        return ResponseEntity(
            ExpenseReportResponse(message = " Expense report reviewed by supervisor successfully!", id = id),
            HttpStatus.OK,
        )
    }
}
