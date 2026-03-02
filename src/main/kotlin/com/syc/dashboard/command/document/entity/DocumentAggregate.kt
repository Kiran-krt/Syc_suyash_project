package com.syc.dashboard.command.document.entity

import com.syc.dashboard.command.document.api.commands.DocumentUpdateStatusCommand
import com.syc.dashboard.command.document.api.commands.DocumentUploadCommand
import com.syc.dashboard.framework.common.document.exceptions.DocumentEventStreamNotExistInEventStoreException
import com.syc.dashboard.framework.core.entity.TenantAggregateRoot
import com.syc.dashboard.query.document.entity.enums.DocumentObjectTypeEnum
import com.syc.dashboard.query.document.entity.enums.DocumentStatusEnum
import com.syc.dashboard.shared.document.events.DocumentStatusUpdatedEvent
import com.syc.dashboard.shared.document.events.DocumentUploadedEvent
import java.util.*

class DocumentAggregate constructor() : TenantAggregateRoot() {
    var active: Boolean = false
    var objectId: String = ""
    var documentEntity: String = ""
    var documentObjectType: DocumentObjectTypeEnum = DocumentObjectTypeEnum.USER
    var documentName: String = ""
    var extension: String = ""
    var cloudFilePath: String = ""
    var status: DocumentStatusEnum = DocumentStatusEnum.ACTIVE

    constructor(command: DocumentUploadCommand) : this() {
        val createdDate = Date()
        raiseEvent(
            DocumentUploadedEvent(
                id = command.id,
                objectId = command.objectId,
                documentEntity = command.documentEntity,
                documentObjectType = command.documentObjectType,
                documentName = command.documentName,
                extension = command.extension,
                cloudFilePath = command.cloudFilePath,
                status = command.status,
                createdDate = createdDate,
            ).buildEvent(command),
        )
    }

    fun apply(event: DocumentUploadedEvent) {
        buildAggregateRoot(event)
        id = event.id
        active = true
        objectId = event.objectId
        documentEntity = event.documentEntity
        documentObjectType = event.documentObjectType
        documentName = event.documentName
        extension = event.extension
        cloudFilePath = event.cloudFilePath
        status = event.status
    }

    fun updateStatusById(command: DocumentUpdateStatusCommand) {
        if (!active) {
            throw DocumentEventStreamNotExistInEventStoreException(
                "Document Status Updated Exception!",
            )
        }
        raiseEvent(
            DocumentStatusUpdatedEvent(
                id = command.id,
                status = command.status,
            ).buildEvent(command),
        )
    }

    fun apply(event: DocumentStatusUpdatedEvent) {
        buildAggregateRoot(event)
        id = event.id
        status = event.status
    }
}
