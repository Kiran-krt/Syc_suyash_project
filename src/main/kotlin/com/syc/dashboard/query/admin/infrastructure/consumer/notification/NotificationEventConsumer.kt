package com.syc.dashboard.query.admin.infrastructure.consumer.notification

import com.syc.dashboard.shared.admin.notification.events.AdminPasswordForgotEventNotification
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface NotificationEventConsumer {

    fun consume(@Payload event: AdminPasswordForgotEventNotification, ack: Acknowledgment)
}
