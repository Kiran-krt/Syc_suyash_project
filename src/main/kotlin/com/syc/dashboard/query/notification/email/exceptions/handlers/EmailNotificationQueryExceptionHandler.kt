package com.syc.dashboard.query.notification.email.exceptions.handlers

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import com.syc.dashboard.query.notification.email.exceptions.EmailNotificationNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class EmailNotificationQueryExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(EmailNotificationNotFoundException::class)
    fun handleEmailNotificationNotFoundException(e: EmailNotificationNotFoundException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), errorCode = ErrorCodes.NOTIFICATION_EMAIL_NOT_FOUND))
    }
}
