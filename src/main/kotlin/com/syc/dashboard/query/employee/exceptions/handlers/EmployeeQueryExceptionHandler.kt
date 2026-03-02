package com.syc.dashboard.query.employee.exceptions.handlers

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import com.syc.dashboard.query.employee.exceptions.EmployeeNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class EmployeeQueryExceptionHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(EmployeeNotFoundException::class)
    fun handleEmployeeNotFoundException(e: EmployeeNotFoundException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), errorCode = ErrorCodes.EMPLOYEE_NOT_FOUND))
    }
}
