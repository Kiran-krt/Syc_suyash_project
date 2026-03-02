package com.syc.dashboard.query.notification.mobile.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.notification.mobile.events.MobileNotificationSentEvent
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)

    fun consume(@Payload event: MobileNotificationSentEvent, ack: Acknowledgment)
}
