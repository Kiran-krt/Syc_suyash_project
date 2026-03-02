package com.syc.dashboard.query.jobcode.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.jobcode.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)
    fun consume(@Payload event: JobCodeRegisteredEvent, ack: Acknowledgment)
    fun consume(@Payload event: JobCodeStatusUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: CostCodeAddedEvent, ack: Acknowledgment)
    fun consume(@Payload event: CostCodeUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: JobCodeUpdatedEvent, ack: Acknowledgment)
}
