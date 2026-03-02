package com.syc.dashboard.query.notification.email.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.notification.email.events.EmailNotificationSentEvent
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)

    fun consume(@Payload event: EmailNotificationSentEvent, ack: Acknowledgment)
}
