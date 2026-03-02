package com.syc.dashboard.query.notification.email.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.notification.email.events.EmailNotificationEvents
import com.syc.dashboard.shared.notification.email.events.EmailNotificationSentEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class EmailNotificationEventConsumer @Autowired constructor(
    @Qualifier("emailNotificationEventHandler")
    private val eventHandler: EventHandler,
) : EventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${EmailNotificationEvents.EMAIL_NOTIFICATION_REPUBLISH_EVENTS}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: BaseEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${EmailNotificationSentEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: EmailNotificationSentEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
