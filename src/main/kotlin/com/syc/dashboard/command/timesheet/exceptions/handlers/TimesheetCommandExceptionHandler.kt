package com.syc.dashboard.command.timesheet.exceptions.handlers

import com.syc.dashboard.command.timesheet.exceptions.TimesheetStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.command.timesheet.exceptions.TimesheetWeekEndingDateAlreadyExistsException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class TimesheetCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(TimesheetStateChangeNotAllowedForInactiveStatusException::class)
    fun handleTimesheetStateChangeNotAllowedForInactiveStatusException(
        e: TimesheetStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.TIMESHEET_STATE_CHANGE_NOT_ALLOWED_FOR_INACTIVE_STATUS))
    }

    @ExceptionHandler(TimesheetWeekEndingDateAlreadyExistsException::class)
    fun handleTimesheetStateChangeNotAllowedForInactiveStatusException(
        e: TimesheetWeekEndingDateAlreadyExistsException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.TIMESHEET_WEEK_ENDING_DATE_ALREADY_EXISTS))
    }
}
