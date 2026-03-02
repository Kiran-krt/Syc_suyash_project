package com.syc.dashboard.query.document.infrastructure.consumer

import com.syc.dashboard.shared.document.events.DocumentStatusUpdatedEvent
import com.syc.dashboard.shared.document.events.DocumentUploadedEvent
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: DocumentUploadedEvent, ack: Acknowledgment)
    fun consume(@Payload event: DocumentStatusUpdatedEvent, ack: Acknowledgment)
}
