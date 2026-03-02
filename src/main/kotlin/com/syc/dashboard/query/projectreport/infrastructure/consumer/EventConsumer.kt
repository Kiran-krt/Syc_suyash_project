package com.syc.dashboard.query.projectreport.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.projectreport.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)

    fun consume(@Payload event: ProjectReportRegisteredEvent, ack: Acknowledgment)

    fun consume(@Payload event: ProjectReportStatusUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: ProjectReportFieldUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: OutfallPhotoUploadEvent, ack: Acknowledgment)

    fun consume(@Payload event: OutfallPhotoStatusUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: OutfallPhotoAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: AppendixAddedEvent, ack: Acknowledgment)

    fun consume(@Payload event: AppendixAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: AppendixStatusUpdatedEvent, ack: Acknowledgment)
}
