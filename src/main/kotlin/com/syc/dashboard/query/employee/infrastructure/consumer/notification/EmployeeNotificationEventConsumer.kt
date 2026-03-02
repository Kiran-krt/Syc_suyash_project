package com.syc.dashboard.query.employee.infrastructure.consumer.notification

import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.employee.notification.events.EmployeePasswordForgotEventNotification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class EmployeeNotificationEventConsumer @Autowired constructor(
    @Qualifier("employeeNotificationEventHandler")
    private val eventHandler: EventHandler,
) : NotificationEventConsumer {
    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${EmployeePasswordForgotEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: EmployeePasswordForgotEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
