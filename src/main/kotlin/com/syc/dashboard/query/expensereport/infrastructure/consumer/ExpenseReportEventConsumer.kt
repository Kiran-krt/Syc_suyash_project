package com.syc.dashboard.query.expensereport.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.expensereport.events.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class ExpenseReportEventConsumer @Autowired constructor(
    @Qualifier("expenseReportEventHandler")
    private val eventHandler: EventHandler,
) : EventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportAddedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportAddedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportEvents.EXPENSE_REPORT_REPUBLISH_EVENTS}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: BaseEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseRowForEmployeeAddedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseRowForEmployeeAddedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportRowDeletedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportRowDeletedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportDeletedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportDeletedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportStatusUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportStatusUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportReviewedByAdminEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportReviewedByAdminEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportSubmittedByEmployeeEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportSubmittedByEmployeeEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportReviewedBySupervisorEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportReviewedBySupervisorEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseReportAllFieldsUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseReportAllFieldsUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseRowForSuyashAddedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseRowForSuyashAddedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseRowsForEmployeeUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseRowsForEmployeeUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ExpenseRowsForSuyashUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ExpenseRowsForSuyashUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
