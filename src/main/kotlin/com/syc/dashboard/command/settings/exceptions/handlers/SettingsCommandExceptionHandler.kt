package com.syc.dashboard.command.settings.exceptions.handlers

import com.syc.dashboard.command.settings.exceptions.SettingsStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.command.settings.exceptions.SettingsWithTenantIdAlreadyExistException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SettingsCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(SettingsStateChangeNotAllowedForInactiveStatusException::class)
    fun handleSettingsStateChangeNotAllowedForInactiveStatusException(e: SettingsStateChangeNotAllowedForInactiveStatusException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.SETTINGS_STATE_CHANGE_NOT_ALLOWED))
    }

    @ExceptionHandler(SettingsWithTenantIdAlreadyExistException::class)
    fun handleSettingsWithTenantIdAlreadyExistException(e: SettingsWithTenantIdAlreadyExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.SETTINGS_WITH_TENANT_ID_ALREADY_EXIST))
    }
}
