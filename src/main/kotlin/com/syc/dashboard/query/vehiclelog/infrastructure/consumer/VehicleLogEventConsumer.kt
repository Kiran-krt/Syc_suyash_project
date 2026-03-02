package com.syc.dashboard.query.vehiclelog.infrastructure.consumer

import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogAllFieldsUpdatedEvent
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogRegisteredEvent
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogStatusUpdatedByIdEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class VehicleLogEventConsumer @Autowired constructor(
    @Qualifier("vehicleLogEventHandler")
    private val eventHandler: EventHandler,
) : EventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${VehicleLogRegisteredEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: VehicleLogRegisteredEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${VehicleLogAllFieldsUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: VehicleLogAllFieldsUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${VehicleLogStatusUpdatedByIdEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: VehicleLogStatusUpdatedByIdEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
