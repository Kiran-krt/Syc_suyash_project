package com.syc.dashboard.query.project.infrastructure.consumer.notification

import com.syc.dashboard.shared.project.notification.events.ProjectRegisteredEventNotification
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface NotificationEventConsumer {

    fun consume(@Payload event: ProjectRegisteredEventNotification, ack: Acknowledgment)
}
