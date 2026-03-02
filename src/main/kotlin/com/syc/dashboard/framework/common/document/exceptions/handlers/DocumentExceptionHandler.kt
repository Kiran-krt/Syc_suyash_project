package com.syc.dashboard.framework.common.document.exceptions.handlers

import com.syc.dashboard.framework.common.document.exceptions.DocumentEventStreamNotExistInEventStoreException
import com.syc.dashboard.framework.common.document.exceptions.DocumentUploadException
import com.syc.dashboard.framework.common.document.exceptions.InvalidDocumentEntityException
import com.syc.dashboard.framework.common.exceptions.ErrorCodes
import com.syc.dashboard.framework.core.dto.BaseResponse
import com.syc.dashboard.framework.core.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class DocumentExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(DocumentUploadException::class)
    fun handleDocumentUploadException(e: DocumentUploadException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.DOCUMENT_UPLOAD_FAILED))
    }

    @ExceptionHandler(DocumentEventStreamNotExistInEventStoreException::class)
    fun handleDocumentEventStreamNotExistInEventStoreException(e: DocumentEventStreamNotExistInEventStoreException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(
                ErrorResponse(
                    message = e.message.toString(),
                    ErrorCodes.DOCUMENT_STATE_CHANGE_NOT_ALLOWED_FOR_INACTIVE_STATUS,
                ),
            )
    }

    @ExceptionHandler(InvalidDocumentEntityException::class)
    fun handleInvalidDocumentEntityException(e: InvalidDocumentEntityException): ResponseEntity<out BaseResponse> {
        log.warn(e.message!!)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(message = e.message.toString(), ErrorCodes.INVALID_DOCUMENT_ENTITY))
    }
}
