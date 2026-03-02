package com.syc.dashboard.query.vehiclelog.infrastructure.consumer

import com.syc.dashboard.shared.vehiclelog.events.VehicleLogAllFieldsUpdatedEvent
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogRegisteredEvent
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogStatusUpdatedByIdEvent
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: VehicleLogRegisteredEvent, ack: Acknowledgment)
    fun consume(@Payload event: VehicleLogAllFieldsUpdatedEvent, ack: Acknowledgment)
    fun consume(@Payload event: VehicleLogStatusUpdatedByIdEvent, ack: Acknowledgment)
}
