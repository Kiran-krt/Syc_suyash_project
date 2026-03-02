package com.syc.dashboard.query.notification.inapp.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.notification.inapp.events.InAppNotificationSentEvent
import com.syc.dashboard.shared.notification.inapp.events.InAppNotificationStatusUpdatedEvent
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)
    fun consume(@Payload event: InAppNotificationSentEvent, ack: Acknowledgment)
    fun consume(@Payload event: InAppNotificationStatusUpdatedEvent, ack: Acknowledgment)
}
