package com.syc.dashboard.command.employee.exception.handlers

import com.syc.dashboard.command.employee.exception.*
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class EmployeeCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(EmployeeAlreadyExistException::class)
    fun handleEmployeeAlreadyExistException(e: EmployeeAlreadyExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.EMPLOYEE_ALREADY_REGISTERED_WITH_EMAIL))
    }

    @ExceptionHandler(EmployeeStateChangeNotAllowedForInactiveStatusException::class)
    fun handleEmployeeStateChangeNotAllowedForInactiveStatusException(
        e: EmployeeStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.EMPLOYEE_STATE_CHANGE_NOT_ALLOWED))
    }

    @ExceptionHandler(EmployeeNotFoundException::class)
    fun handleEmployeeNotFoundException(e: EmployeeNotFoundException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.EMPLOYEE_NOT_FOUND_WITH_EMAIL))
    }

    @ExceptionHandler(EmployeePasswordMisMatchException::class)
    fun handleEmployeeNotFoundException(e: EmployeePasswordMisMatchException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.EMPLOYEE_PASSWORD_MISMATCH))
    }

    @ExceptionHandler(EmployeeRoleInvalidException::class)
    fun handleEmployeeRoleInvalidException(e: EmployeeRoleInvalidException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.EMPLOYEE_ROLE_INVALID))
    }
}
