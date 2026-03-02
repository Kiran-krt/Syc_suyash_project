package com.syc.dashboard.query.settings.infrastructure.consumer

import com.syc.dashboard.shared.settings.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {
    fun consume(@Payload event: SettingsRegisteredEvent, ack: Acknowledgment)
    fun consume(@Payload event: SettingsDateFormatUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: SettingsTimeZoneUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: SettingsTimesheetDelayInHoursUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: SettingsDeletedEvent, ack: Acknowledgment)
    fun consume(@Payload event: SettingsStatusUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: SettingsMileageRateUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseTypeAddedEvent, ack: Acknowledgment)
    fun consume(@Payload event: ExpenseTypeAllFieldsUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: PayrollItemAddedEvent, ack: Acknowledgment)
    fun consume(@Payload event: PayrollItemAllFieldsUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: SettingsYearlyQuarterUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: VehicleInfoRegisteredEvent, ack: Acknowledgment)
    fun consume(@Payload event: VehicleInfoAllFieldsUpdatedEvent, ack: Acknowledgment)
}
