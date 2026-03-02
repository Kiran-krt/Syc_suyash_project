package com.syc.dashboard.query.notification.mobile.exceptions.handlers

import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import com.syc.dashboard.query.notification.email.exceptions.EmailNotificationNotFoundException
import com.syc.dashboard.query.notification.mobile.exceptions.MobileNotificationNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MobileNotificationQueryExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(EmailNotificationNotFoundException::class)
    fun handleMobileNotificationNotFoundException(e: MobileNotificationNotFoundException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), errorCode = ErrorCodes.NOTIFICATION_MOBILE_NOT_FOUND))
    }
}
