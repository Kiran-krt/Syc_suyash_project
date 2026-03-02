package com.syc.dashboard.query.jobcode.infrastructure.consumer.notification

import com.syc.dashboard.shared.jobcode.notification.events.CostCodeAddedEventNotification
import com.syc.dashboard.shared.jobcode.notification.events.JobCodeRegisteredEventNotification
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface NotificationEventConsumer {

    fun consume(@Payload event: JobCodeRegisteredEventNotification, ack: Acknowledgment)
    fun consume(@Payload event: CostCodeAddedEventNotification, ack: Acknowledgment)
}
