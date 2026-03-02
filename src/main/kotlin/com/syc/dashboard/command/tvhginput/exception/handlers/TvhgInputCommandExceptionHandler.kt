package com.syc.dashboard.command.tvhginput.exception.handlers

import com.syc.dashboard.command.tvhginput.exception.TvhgInputEventStreamNotExistInEventStoreException
import com.syc.dashboard.command.tvhginput.exception.TvhgInputStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class TvhgInputCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(TvhgInputStateChangeNotAllowedForInactiveStatusException::class)
    fun handleTvhgInputStateChangeNotAllowedForInactiveStatusException(
        e: TvhgInputStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.TVHG_INPUT_STATE_CHANGE_NOT_ALLOWED))
    }

    @ExceptionHandler(TvhgInputEventStreamNotExistInEventStoreException::class)
    fun handleTvhgInputEventStreamNotExistInEventStoreException(e: TvhgInputEventStreamNotExistInEventStoreException): ResponseEntity<out BaseResponse> {
        log.warn(e.message)
        return ResponseEntity.badRequest()
            .body(
                e.message?.let {
                    ErrorResponse(
                        message = it,
                        ErrorCodes.TVHG_INPUT_EVENT_STREAM_NOT_EXIST_IN_EVENT_STORE,
                    )
                },
            )
    }
}
