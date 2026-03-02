package com.syc.dashboard.query.employee.infrastructure.consumer.notification

import com.syc.dashboard.shared.employee.notification.events.EmployeePasswordForgotEventNotification
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface NotificationEventConsumer {

    fun consume(@Payload event: EmployeePasswordForgotEventNotification, ack: Acknowledgment)
}
