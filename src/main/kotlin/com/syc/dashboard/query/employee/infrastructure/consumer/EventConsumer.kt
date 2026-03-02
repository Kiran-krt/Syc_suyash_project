package com.syc.dashboard.query.employee.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.employee.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {
    fun consume(@Payload event: EmployeeRegisteredEvent, ack: Acknowledgment)
    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeeAllFieldsUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeePasswordResetEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeePasswordUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeeLoggedInEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeeLoggedOutEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeeRoleUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeeProfileUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeeEmailUpdatedByIdEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeeMobileDeviceInfoUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeePasswordForgotEvent, ack: Acknowledgment)
    fun consume(@Payload event: EmployeeStatusUpdatedByIdEvent, ack: Acknowledgment)
}
