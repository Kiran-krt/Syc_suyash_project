package com.syc.dashboard.query.project.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.project.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)

    fun consume(@Payload event: ProjectRegisteredEvent, ack: Acknowledgment)

    fun consume(@Payload event: ProjectStatusUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: ProjectAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: JobCodeAddedEvent, ack: Acknowledgment)

    fun consume(@Payload event: JobCodeUpdatedByProjectIdEvent, ack: Acknowledgment)
}
