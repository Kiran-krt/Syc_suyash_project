package com.syc.dashboard.query.notification.inapp.exceptions.handlers

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import com.syc.dashboard.query.notification.inapp.exceptions.InAppNotificationNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class InAppNotificationQueryExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(InAppNotificationNotFoundException::class)
    fun handleInAppNotificationNotFoundException(e: InAppNotificationNotFoundException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), errorCode = ErrorCodes.NOTIFICATION_IN_APP_NOT_FOUND))
    }
}
