package com.syc.dashboard.query.settings.exceptions.handlers

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import com.syc.dashboard.query.settings.exceptions.SettingsNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SettingsQueryExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(SettingsNotFoundException::class)
    fun handleSettingsNotFoundException(e: SettingsNotFoundException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), errorCode = ErrorCodes.SETTINGS_NOT_FOUND))
    }
}
