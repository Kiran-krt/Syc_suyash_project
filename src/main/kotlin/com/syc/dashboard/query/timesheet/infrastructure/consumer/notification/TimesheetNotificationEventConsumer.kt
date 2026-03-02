package com.syc.dashboard.query.timesheet.infrastructure.consumer.notification

import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.timesheet.events.*
import com.syc.dashboard.shared.timesheet.notification.events.TimesheetAdminCommentsUpdatedByIdEventNotification
import com.syc.dashboard.shared.timesheet.notification.events.TimesheetEmployeeCommentsUpdatedByIdEventNotification
import com.syc.dashboard.shared.timesheet.notification.events.TimesheetManagerCommentsUpdatedByIdEventNotification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class TimesheetNotificationEventConsumer @Autowired constructor(
    @Qualifier("timesheetNotificationEventHandler")
    private val eventHandler: EventHandler,
) : NotificationEventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${TimesheetEmployeeCommentsUpdatedByIdEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: TimesheetEmployeeCommentsUpdatedByIdEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${TimesheetManagerCommentsUpdatedByIdEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: TimesheetManagerCommentsUpdatedByIdEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${TimesheetAdminCommentsUpdatedByIdEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: TimesheetAdminCommentsUpdatedByIdEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
