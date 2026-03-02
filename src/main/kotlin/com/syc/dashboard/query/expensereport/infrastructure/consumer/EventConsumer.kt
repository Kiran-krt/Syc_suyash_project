package com.syc.dashboard.query.expensereport.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.expensereport.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseReportAddedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseRowForEmployeeAddedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseReportRowDeletedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseReportDeletedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseReportStatusUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseReportReviewedByAdminEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseReportSubmittedByEmployeeEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseReportReviewedBySupervisorEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseReportAllFieldsUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseRowForSuyashAddedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseRowsForSuyashUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseRowsForEmployeeUpdatedEvent, ack: Acknowledgment)
}
