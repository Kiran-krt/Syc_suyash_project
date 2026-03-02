package com.syc.dashboard.query.document.infrastructure.handlers

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.document.entity.Document
import com.syc.dashboard.query.document.repository.jpa.DocumentRepository
import com.syc.dashboard.shared.document.events.DocumentStatusUpdatedEvent
import com.syc.dashboard.shared.document.events.DocumentUploadedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DocumentEventHandler @Autowired constructor(
    private val documentRepository: DocumentRepository,
) : EventHandler {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    private fun on(event: DocumentUploadedEvent) {
        val document = Document(
            id = event.id,
            objectId = event.objectId,
            documentEntity = event.documentEntity,
            documentObjectType = event.documentObjectType,
            documentName = event.documentName,
            extension = event.extension,
            cloudFilePath = event.cloudFilePath,
            status = event.status,
        ).buildEntity(event) as Document
        documentRepository.save(document)
    }

    private fun on(event: DocumentStatusUpdatedEvent) {
        val documentOptional = documentRepository.findById(event.id)
        if (documentOptional.isEmpty) {
            return
        }
        documentOptional.get().status = event.status
        documentRepository.save(documentOptional.get())
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is DocumentUploadedEvent -> on(event)
            is DocumentStatusUpdatedEvent -> on(event)
        }
    }
}
