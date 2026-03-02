package com.syc.dashboard.command.tvhgConfig.exception.handlers

import com.syc.dashboard.command.tvhgConfig.exception.TvhgConfigEventStreamNotExistInEventStoreException
import com.syc.dashboard.command.tvhgConfig.exception.TvhgConfigStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class TvhgConfigCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(TvhgConfigStateChangeNotAllowedForInactiveStatusException::class)
    fun handleTvhgConfigStateChangeNotAllowedForInactiveStatusException(
        e: TvhgConfigStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.TVHG_CONFIG_STATE_CHANGE_NOT_ALLOWED))
    }

    @ExceptionHandler(TvhgConfigEventStreamNotExistInEventStoreException::class)
    fun handleTvhgConfigEventStreamNotExistInEventStoreException(e: TvhgConfigEventStreamNotExistInEventStoreException): ResponseEntity<out BaseResponse> {
        log.warn(e.message)
        return ResponseEntity.badRequest()
            .body(
                e.message?.let {
                    ErrorResponse(
                        message = it,
                        ErrorCodes.TVHG_CONFIG_EVENT_STREAM_NOT_EXIST_IN_EVENT_STORE,
                    )
                },
            )
    }
}
