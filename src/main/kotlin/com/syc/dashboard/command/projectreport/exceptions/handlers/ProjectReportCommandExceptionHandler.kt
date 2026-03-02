package com.syc.dashboard.command.projectreport.exceptions.handlers

import com.syc.dashboard.command.projectreport.exceptions.ProjectReportStateChangeNotAllowedForInactiveStatusException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ProjectReportCommandExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ProjectReportStateChangeNotAllowedForInactiveStatusException::class)
    fun handleProjectStateChangeNotAllowedForInactiveStatusException(
        e: ProjectReportStateChangeNotAllowedForInactiveStatusException,
    ): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.PROJECT_REPORT_STATE_CHANGE_NOT_ALLOWED))
    }
}
