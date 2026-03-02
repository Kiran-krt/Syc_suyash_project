package com.syc.dashboard.query.project.infrastructure.consumer.notification

import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.project.notification.events.ProjectRegisteredEventNotification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class ProjectNotificationEventConsumer @Autowired constructor(
    @Qualifier("projectNotificationEventHandler")
    private val eventHandler: EventHandler,
) : NotificationEventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ProjectRegisteredEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ProjectRegisteredEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
