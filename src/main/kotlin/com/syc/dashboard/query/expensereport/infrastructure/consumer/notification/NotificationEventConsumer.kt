package com.syc.dashboard.query.expensereport.infrastructure.consumer.notification

import com.syc.dashboard.shared.expensereport.notification.events.ExpenseReportReviewedByAdminEventNotification
import com.syc.dashboard.shared.expensereport.notification.events.ExpenseReportReviewedBySupervisorEventNotification
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface NotificationEventConsumer {

    fun consume(@Payload event: ExpenseReportReviewedByAdminEventNotification, ack: Acknowledgment)

    fun consume(@Payload event: ExpenseReportReviewedBySupervisorEventNotification, ack: Acknowledgment)
}
