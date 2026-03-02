package com.syc.dashboard.command.admin.exceptions.handlers

import com.syc.dashboard.command.admin.exceptions.AdminAlreadyExistException
import com.syc.dashboard.command.admin.exceptions.AdminNotExistException
import com.syc.dashboard.command.admin.exceptions.AdminStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AdminCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(AdminAlreadyExistException::class)
    fun handleAdminAlreadyExistException(e: AdminAlreadyExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.ADMIN_ALREADY_REGISTERED_WITH_EMAIL))
    }

    @ExceptionHandler(AdminNotExistException::class)
    fun handleAdminNotExistException(e: AdminNotExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.ADMIN_NOT_EXIST_WITH_EMAIL))
    }

    @ExceptionHandler(AdminStateChangeNotAllowedForInactiveStatusException::class)
    fun handleAdminStateChangeNotAllowedForInactiveStatusException(
        e: AdminStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.ADMIN_STATE_CHANGE_NOT_ALLOWED_FOR_INACTIVE_STATUS))
    }
}
