package com.syc.dashboard.query.admin.infrastructure.consumer.notification

import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.admin.notification.events.AdminPasswordForgotEventNotification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class AdminNotificationEventConsumer @Autowired constructor(
    @Qualifier("adminNotificationEventHandler")
    private val eventHandler: EventHandler,
) : NotificationEventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${AdminPasswordForgotEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: AdminPasswordForgotEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
