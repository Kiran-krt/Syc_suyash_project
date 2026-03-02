package com.syc.dashboard.command.document.api.controllers.v1

import com.syc.dashboard.command.document.api.commands.DocumentUploadCommand
import com.syc.dashboard.framework.common.config.DocumentConfig
import com.syc.dashboard.framework.common.document.exceptions.InvalidDocumentEntityException
import com.syc.dashboard.framework.common.document.services.DocumentStorageService
import com.syc.dashboard.framework.core.controllers.RootController
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.query.document.dto.DocumentDto
import com.syc.dashboard.query.document.entity.enums.DocumentObjectTypeEnum
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*

@RestController
@Tags(Tag(name = "V1 Document"))
@CrossOrigin
class DocumentUploadController @Autowired constructor(
    private val commandDispatcher: CommandDispatcher,
    private val documentStorageService: DocumentStorageService,
    private val documentConfig: DocumentConfig,
) : RootController() {

    @PostMapping("/api/v1/document/upload")
    fun uploadFile(
        command: DocumentUploadCommand,
        @RequestPart file: Mono<FilePart>,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): Mono<ResponseEntity<BaseDto>> {
        if (command.documentObjectType == DocumentObjectTypeEnum.SYSTEM) {
            command.objectId = documentConfig.defaultSystemId
        }

        validateDocumentObjectTypeAndEntity(command)

        val id = UUID.randomUUID().toString()
        command.id = id

        val cloudFilePath = buildCloudFilePath(command)

        return documentStorageService
            .upload(
                tenantId = command.tenantId,
                cloudFilePath = cloudFilePath,
                file = file,
            )
            .map {
                val extension = ".${FilenameUtils.getExtension(it)}"
                command.documentName = FilenameUtils.getBaseName(it)
                command.extension = extension
                command.cloudFilePath = cloudFilePath + extension
                commandDispatcher.send(command)

                it
            }
            .map {
                val dto = DocumentDto()
                BeanUtils.copyProperties(command, dto)
                ResponseEntity
                    .ok()
                    .body(dto)
            }
    }

    fun validateDocumentObjectTypeAndEntity(command: DocumentUploadCommand) {
        when (command.documentObjectType) {
            DocumentObjectTypeEnum.USER -> {
                if (!documentConfig.userEntities.contains(command.documentEntity)) {
                    throw InvalidDocumentEntityException(
                        "Invalid document entity '${command.documentEntity}' " +
                            "is selected for object '${command.documentObjectType}'!",
                    )
                }
            }

            DocumentObjectTypeEnum.SYSTEM -> {
                if (!documentConfig.systemEntities.contains(command.documentEntity)) {
                    throw InvalidDocumentEntityException(
                        "Invalid document entity '${command.documentEntity}' " +
                            "is selected for object '${command.documentObjectType}'!",
                    )
                }
            }

            DocumentObjectTypeEnum.PROJECT_REPORT -> {
                if (!documentConfig.projectReportEntities.contains(command.documentEntity)) {
                    throw InvalidDocumentEntityException(
                        "Invalid document entity '${command.documentEntity}' " +
                            "is selected for object '${command.documentObjectType}'!",
                    )
                }
            }
        }

        if (command.objectId.isEmpty()) {
            throw InvalidDocumentEntityException(
                "Object id is not specified for document object type '${command.documentObjectType}'!",
            )
        }
    }

    private fun buildCloudFilePath(command: DocumentUploadCommand): String =
        command.documentObjectType.folderName +
            "/" + command.objectId +
            "/" + command.documentEntity +
            "/" + command.id
}
