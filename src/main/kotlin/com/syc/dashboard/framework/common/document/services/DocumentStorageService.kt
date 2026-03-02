package com.syc.dashboard.framework.common.document.services

import com.syc.dashboard.framework.common.config.DocumentConfig
import com.syc.dashboard.framework.common.document.client.WebDavClient
import com.syc.dashboard.framework.common.document.exceptions.DocumentUploadException
import com.syc.dashboard.framework.core.dto.BaseResponse
import org.apache.commons.io.FilenameUtils
import org.apache.commons.text.StringSubstitutor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.io.InputStream

@Service
class DocumentStorageService @Autowired constructor(
    private val documentConfig: DocumentConfig,
    private val webDavClient: WebDavClient,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    private val cloudDocumentWebClient = WebClient.builder()
        .defaultHeader("Authorization", "Basic ${documentConfig.cloudAuth}")
        .codecs { it.defaultCodecs().maxInMemorySize(-1) }
        .build()

    fun upload(tenantId: String, cloudFilePath: String, file: Mono<FilePart>): Mono<String> {
        return file
            .flatMap { filePart ->
                cloudDocumentWebClient
                    .put()
                    .uri(
                        StringSubstitutor(mapOf(Pair("tenantId", tenantId)))
                            .replace(documentConfig.cloudBaseUserUrl) + cloudFilePath +
                            ".${FilenameUtils.getExtension(filePart.filename())}",
                    )
                    .body(BodyInserters.fromDataBuffers(filePart.content()))
                    .exchangeToMono {
                        when {
                            it.statusCode().isError -> {
                                val safeError = "Error while uploading file '${filePart.filename()}'."
                                log.error(safeError)
                                it.bodyToMono(BaseResponse::class.java)
                                    .handle { baseResponse, sink ->
                                        sink.error(DocumentUploadException(baseResponse.message))
                                    }
                            }

                            it.statusCode().is2xxSuccessful -> {
                                log.info("Document successfully uploaded to '$cloudFilePath'")
                                Mono.just(filePart.filename())
                            }

                            else -> {
                                val safeError = "Error while uploading file '${filePart.filename()}."
                                log.error(safeError)
                                throw DocumentUploadException(safeError)
                            }
                        }
                    }
            }
    }

    fun viewWithWebDav(tenantId: String, cloudFilePath: String): InputStream {
        return webDavClient.getCloudFile(
            StringSubstitutor(mapOf(Pair("tenantId", tenantId)))
                .replace(documentConfig.cloudBaseUserUrl) + cloudFilePath,
        )
    }

    fun view(tenantId: String, cloudFilePath: String): Mono<DataBuffer> {
        return cloudDocumentWebClient
            .get()
            .uri(
                StringSubstitutor(mapOf(Pair("tenantId", tenantId)))
                    .replace(documentConfig.cloudBaseUserUrl) + cloudFilePath,
            )
            .retrieve()
            .bodyToMono(DataBuffer::class.java)
    }

    fun thumbnail(tenantId: String, cloudFilePath: String): Mono<DataBuffer> {
        val fileUrl = StringSubstitutor(mapOf(Pair("tenantId", tenantId)))
            .replace(documentConfig.cloudBaseUserUrl) + cloudFilePath

        val cloudFileMetadata = webDavClient.getCloudFileMetadata(fileUrl)

        val thumbnailUrl = if (cloudFileMetadata.hasPreview) {
            "${documentConfig.cloudBaseUrl}/index.php/core/preview?fileId=${cloudFileMetadata.fileId}"
        } else {
            fileUrl
        }

        return cloudDocumentWebClient
            .get()
            .uri(thumbnailUrl)
            .retrieve()
            .bodyToMono(DataBuffer::class.java)
    }
}
