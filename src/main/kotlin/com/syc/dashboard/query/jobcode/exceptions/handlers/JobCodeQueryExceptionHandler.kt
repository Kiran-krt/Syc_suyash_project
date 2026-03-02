package com.syc.dashboard.query.jobcode.exceptions.handlers

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import com.syc.dashboard.query.jobcode.exceptions.CostCodeNotFoundWithIdException
import com.syc.dashboard.query.jobcode.exceptions.JobCodeNotFoundWithIdException
import com.syc.dashboard.query.jobcode.exceptions.JobCodeWithCostCodeNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class JobCodeQueryExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(JobCodeNotFoundWithIdException::class)
    fun handleJobCodeNotFoundException(e: JobCodeNotFoundWithIdException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), errorCode = ErrorCodes.JOBCODE_NOT_FOUND))
    }

    @ExceptionHandler(JobCodeWithCostCodeNotFoundException::class)
    fun handleJobCodeNotFoundException(e: JobCodeWithCostCodeNotFoundException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), errorCode = ErrorCodes.JOBCODE_WITH_COSTCODE_NOT_FOUND))
    }

    @ExceptionHandler(CostCodeNotFoundWithIdException::class)
    fun handleCostCodeNotFoundWithIdException(e: CostCodeNotFoundWithIdException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), errorCode = ErrorCodes.COSTCODE_NOT_FOUND))
    }
}
