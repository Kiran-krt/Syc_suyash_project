package com.syc.dashboard.query.expensereport.infrastructure.consumer.notification

import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.expensereport.notification.events.ExpenseReportReviewedByAdminEventNotification
import com.syc.dashboard.shared.expensereport.notification.events.ExpenseReportReviewedBySupervisorEventNotification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class ExpenseReportNotificationEventConsumer @Autowired constructor(
    @Qualifier("expenseReportNotificationEventHandler")
    private val eventHandler: EventHandler,
) : NotificationEventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportReviewedByAdminEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportReviewedByAdminEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportReviewedBySupervisorEventNotification.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportReviewedBySupervisorEventNotification, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
