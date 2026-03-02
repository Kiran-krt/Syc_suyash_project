package com.syc.dashboard.query.jobcode.infrastructure.consumer.notification

import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.jobcode.notification.events.CostCodeAddedEventNotification
import com.syc.dashboard.shared.jobcode.notification.events.JobCodeRegisteredEventNotification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class JobCodeNotificationEventConsumer @Autowired constructor(
    @Qualifier("jobCodeNotificationEventHandler")
    private val eventHandler: EventHandler,
) : NotificationEventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${JobCodeRegisteredEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: JobCodeRegisteredEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${CostCodeAddedEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: CostCodeAddedEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
