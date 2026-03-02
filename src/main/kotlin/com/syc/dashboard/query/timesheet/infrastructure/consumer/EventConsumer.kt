package com.syc.dashboard.query.timesheet.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.timesheet.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetRegisteredEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetDayDetailsUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetAdminCommentsUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetEmployeeCommentsUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetManagerCommentsUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetStatusUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetRowDeletedEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetUpdatedWithTimesheetRowsEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetApproverUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetWeekStartingDateUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: TimesheetWeekEndingDateUpdatedByIdEvent, ack: Acknowledgment)
}
