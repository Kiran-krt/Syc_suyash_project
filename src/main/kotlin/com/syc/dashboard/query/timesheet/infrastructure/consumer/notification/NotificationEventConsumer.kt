package com.syc.dashboard.query.timesheet.infrastructure.consumer.notification

import com.syc.dashboard.shared.timesheet.notification.events.TimesheetAdminCommentsUpdatedByIdEventNotification
import com.syc.dashboard.shared.timesheet.notification.events.TimesheetEmployeeCommentsUpdatedByIdEventNotification
import com.syc.dashboard.shared.timesheet.notification.events.TimesheetManagerCommentsUpdatedByIdEventNotification
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface NotificationEventConsumer {

    fun consume(@Payload event: TimesheetEmployeeCommentsUpdatedByIdEventNotification, ack: Acknowledgment)

    fun consume(@Payload event: TimesheetManagerCommentsUpdatedByIdEventNotification, ack: Acknowledgment)

    fun consume(@Payload event: TimesheetAdminCommentsUpdatedByIdEventNotification, ack: Acknowledgment)
}
