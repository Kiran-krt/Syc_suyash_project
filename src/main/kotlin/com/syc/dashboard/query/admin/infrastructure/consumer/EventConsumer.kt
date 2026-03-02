package com.syc.dashboard.query.admin.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.admin.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: AdminRegisteredEvent, ack: Acknowledgment)
    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminPasswordUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminFullNameUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminLoggedInEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminLoggedOutEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminProfileUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminEmailUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminStatusUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminMobileDeviceInfoUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: AdminPasswordForgotEvent, ack: Acknowledgment)
}
