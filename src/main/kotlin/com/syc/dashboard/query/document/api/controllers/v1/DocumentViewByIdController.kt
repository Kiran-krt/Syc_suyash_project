package com.syc.dashboard.query.document.api.controllers.v1

import com.syc.dashboard.framework.common.document.services.DocumentStorageService
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.infrastructure.QueryDispatcher
import com.syc.dashboard.query.document.api.queries.DocumentViewByIdQuery
import com.syc.dashboard.query.document.dto.DocumentDto
import com.syc.dashboard.query.document.exceptions.DocumentNotFoundException
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.util.MimeType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.net.URLConnection

@RestController
@Tags(Tag(name = "V1 Document"))
@CrossOrigin
class DocumentViewByIdController @Autowired constructor(
    private val queryDispatcher: QueryDispatcher,
    private val documentStorageService: DocumentStorageService,
) : RootController() {

    @GetMapping("/api/v1/document/{id}/view")
    fun getDocumentById(
        @PathVariable("id") id: String,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): Mono<ResponseEntity<Mono<DataBuffer>>> {
        val query = buildQuery(DocumentViewByIdQuery(id = id))

        return try {
            queryDispatcher.send(query = query).toMono()
                .map { documentDto ->
                    documentDto as DocumentDto

                    Pair(
                        documentDto,
                        documentStorageService.view(tenantId, documentDto.cloudFilePath),
                    )
                }
                .map {
                    val fileName = it.first.documentName + it.first.extension
                    ResponseEntity
                        .ok()
                        .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"$fileName\"",
                        )
                        .contentType(
                            MediaType
                                .asMediaType(MimeType.valueOf(URLConnection.guessContentTypeFromName(fileName))),
                        )
                        .body(it.second)
                }
        } catch (e: Exception) {
            val safeErrorMessage = "Failed to get document by id request for id '$id'!"
            log.error(safeErrorMessage)
            log.error(e.message)

            throw DocumentNotFoundException(safeErrorMessage)
        }
    }
}
