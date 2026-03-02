package com.syc.dashboard.command.jobcode.exceptions.handlers

import com.syc.dashboard.command.jobcode.exceptions.CostCodeAlreadyExistException
import com.syc.dashboard.command.jobcode.exceptions.JobCodeAlreadyExistException
import com.syc.dashboard.command.jobcode.exceptions.JobCodeStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class JobCodeCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(JobCodeAlreadyExistException::class)
    fun handleJobCodeAlreadyExistException(e: JobCodeAlreadyExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.JOBCODE_ALREADY_REGISTERED_WITH_ID))
    }

    @ExceptionHandler(CostCodeAlreadyExistException::class)
    fun handleCostCodeAlreadyExistException(e: CostCodeAlreadyExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.COSTCODE_ALREADY_REGISTERED_WITH_CODE))
    }

    @ExceptionHandler(JobCodeStateChangeNotAllowedForInactiveStatusException::class)
    fun handleJobCodeStateChangeNotAllowedForInactiveStatusException(
        e: JobCodeStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.JOBCODE_STATE_CHANGE_NOT_ALLOWED))
    }
}
