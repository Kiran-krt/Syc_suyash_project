package com.syc.dashboard.framework.core.exceptions.handlers

import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import com.syc.dashboard.framework.core.exceptions.EventNameMismatchException
import com.syc.dashboard.framework.core.exceptions.FrameworkErrorCodes
import com.syc.dashboard.framework.core.exceptions.TenantIdNotFoundInRequestHeaderException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class FrameworkExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(TenantIdNotFoundInRequestHeaderException::class)
    fun handleTenantIdNotFoundInRequestHeaderException(e: TenantIdNotFoundInRequestHeaderException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), FrameworkErrorCodes.TENANT_ID_NOT_FOUND_IN_REQUEST_HEADER))
    }

    @ExceptionHandler(EventNameMismatchException::class)
    fun handleEventNameMismatchException(e: EventNameMismatchException): ResponseEntity<out BaseResponse> {
        log.warn(e.message)
        return ResponseEntity.badRequest()
            .body(e.message?.let { ErrorResponse(message = it, FrameworkErrorCodes.EVENT_NAME_MISMATCH) })
    }
}
