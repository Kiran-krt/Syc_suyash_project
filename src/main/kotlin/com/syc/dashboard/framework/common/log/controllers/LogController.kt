package com.syc.dashboard.framework.common.log.controllers

import com.syc.dashboard.framework.core.controllers.RootController
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.apache.commons.io.input.ReversedLinesFileReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.io.File
import java.nio.charset.Charset

@RestController
@Tags(Tag(name = "V1 Logs"))
@CrossOrigin
class LogController : RootController() {

    @Value("\${logging.file.name}")
    private val loggingFile: String? = null

    @GetMapping("/api/v1/logs/bylinenumber/{noOfLines}")
    fun getLogsByLineNumber(
        @PathVariable noOfLines: Int = LOG_NUMBER_OF_LINES,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): ResponseEntity<Flux<String>> {
        return ResponseEntity.ok(
            ReversedLinesFileReader(loggingFile?.let { File(it) }, Charset.defaultCharset())
                .readLines(noOfLines)
                .stream()
                .toFlux()
                .map { "$it\n" },
        )
    }

    companion object {
        private const val LOG_NUMBER_OF_LINES = 200
    }
}
