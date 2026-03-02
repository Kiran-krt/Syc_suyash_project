package com.syc.dashboard.command.vehiclelog.exceptions.handlers

import com.syc.dashboard.command.vehiclelog.exceptions.VehicleLogEventStreamNotExistInEventStoreException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class VehicleLogCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(VehicleLogEventStreamNotExistInEventStoreException::class)
    fun handleVehicleLogAlreadyExistException(e: VehicleLogEventStreamNotExistInEventStoreException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.VEHICLE_LOG_EVENT_STREAM_NOT_EXIST_IN_EVENT_STORE))
    }
}
