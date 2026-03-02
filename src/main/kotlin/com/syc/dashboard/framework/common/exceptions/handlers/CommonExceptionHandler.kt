package com.syc.dashboard.framework.common.exceptions.handlers

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.common.exceptions.InvalidDateFormatException
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CommonExceptionHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(InvalidDateFormatException::class)
    fun handleInvalidDateFormatException(
        e: InvalidDateFormatException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.INVALID_DATE_FORMAT))
    }
}
