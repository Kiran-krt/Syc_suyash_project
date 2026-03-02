package com.syc.dashboard.command.expensereport.api.controllers.v1

import com.syc.dashboard.command.expensereport.api.commands.DeleteExpenseReportRowCommand
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
class DeleteExpenseReportRowController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
) : RootController() {

    @DeleteMapping("/api/v1/expensereport/{expenseReportId}/expenserow/{expenseReportRowId}/delete")
    fun deleteExpenseReportRow(
        @PathVariable expenseReportId: String,
        @PathVariable expenseReportRowId: String,
        @RequestBody command: DeleteExpenseReportRowCommand,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<BaseResponse> {
        command.expenseReportId = expenseReportId
        command.id = expenseReportRowId

        commandDispatcher.send(command)
        return ResponseEntity(
            ExpenseReportResponse(message = "Expense report row deleted successfully!", id = expenseReportRowId),
            HttpStatus.OK,
        )
    }
}
