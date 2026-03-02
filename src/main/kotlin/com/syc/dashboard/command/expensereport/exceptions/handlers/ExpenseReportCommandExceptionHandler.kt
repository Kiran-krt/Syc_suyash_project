package com.syc.dashboard.command.expensereport.exceptions.handlers

import com.syc.dashboard.command.expensereport.exceptions.ExpenseReportNotExistException
import com.syc.dashboard.command.expensereport.exceptions.ExpenseReportPeriodAlreadyExistException
import com.syc.dashboard.command.expensereport.exceptions.ExpenseReportStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExpenseReportCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ExpenseReportPeriodAlreadyExistException::class)
    fun handleExpenseReportPeriodAlreadyExistException(e: ExpenseReportPeriodAlreadyExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.EXPENSE_REPORT_ALREADY_ADDED_FOR_SPECIFIED_PERIOD))
    }

    @ExceptionHandler(ExpenseReportNotExistException::class)
    fun handleExpenseReportNotExistException(e: ExpenseReportNotExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.EXPENSE_REPORT_NOT_EXIST))
    }

    @ExceptionHandler(ExpenseReportStateChangeNotAllowedForInactiveStatusException::class)
    fun handleExpenseReportStateChangeNotAllowedForInactiveStatusException(
        e: ExpenseReportStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(
                ErrorResponse(
                    message = e.message.toString(),
                    ErrorCodes.EXPENSE_REPORT_STATE_CHANGE_NOT_ALLOWED_FOR_INACTIVE_STATUS,
                ),
            )
    }
}
