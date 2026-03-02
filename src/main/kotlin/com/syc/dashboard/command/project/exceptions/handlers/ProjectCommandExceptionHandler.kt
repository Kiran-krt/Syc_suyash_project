package com.syc.dashboard.command.project.exceptions.handlers

import com.syc.dashboard.command.project.exceptions.ProjectAlreadyExistException
import com.syc.dashboard.command.project.exceptions.ProjectDoesNotExistException
import com.syc.dashboard.command.project.exceptions.ProjectStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ProjectCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ProjectAlreadyExistException::class)
    fun handleProjectAlreadyExistException(e: ProjectAlreadyExistException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.PROJECT_ALREADY_REGISTERED_WITH_ID))
    }

    @ExceptionHandler(ProjectStateChangeNotAllowedForInactiveStatusException::class)
    fun handleProjectStateChangeNotAllowedForInactiveStatusException(
        e: ProjectStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.PROJECT_STATE_CHANGE_NOT_ALLOWED))
    }

    @ExceptionHandler(ProjectDoesNotExistException::class)
    fun handleProjectDoesNotExistException(
        e: ProjectDoesNotExistException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.PROJECT_DOES_NOT_EXIST))
    }
}
