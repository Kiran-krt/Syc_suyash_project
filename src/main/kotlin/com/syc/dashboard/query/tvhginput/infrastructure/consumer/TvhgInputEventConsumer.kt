package com.syc.dashboard.query.tvhginput.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.shared.tvhginput.events.*
import com.syc.dashboard.shared.tvhginput.events.StructureInformationAddedEvent
import com.syc.dashboard.shared.tvhginput.events.TvhgInputEvents
import com.syc.dashboard.shared.tvhginput.events.TvhgInputRegisteredEvent
import com.syc.dashboard.shared.tvhginput.events.TvhgInputStatusUpdatedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class TvhgInputEventConsumer @Autowired constructor(
    @Qualifier("tvhgInputEventHandler")
    private val eventHandler: EventHandler,
) : EventConsumer {

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${TvhgInputRegisteredEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: TvhgInputRegisteredEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${TvhgInputEvents.TVHG_REPUBLISH_EVENTS}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: BaseEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${TvhgInputStatusUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: TvhgInputStatusUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${TvhgInputAllFieldsUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: TvhgInputAllFieldsUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${AddedProjectInformationInTvhgInputEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: AddedProjectInformationInTvhgInputEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${StructureInformationAddedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: StructureInformationAddedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${StructureInformationAllFieldUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: StructureInformationAllFieldUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${ProjectInformationAllFieldUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: ProjectInformationAllFieldUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${HydrologicInformationAddedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: HydrologicInformationAddedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${HydrologicInformationAllFieldUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: HydrologicInformationAllFieldUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${AddedPipeInformationTvhgInputEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: AddedPipeInformationTvhgInputEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${StructureDrawingDataAddedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: StructureDrawingDataAddedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${PipeInformationAllFieldUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: PipeInformationAllFieldUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${StructureDrawingDataAllFieldsUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: StructureDrawingDataAllFieldsUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${AddedInletControlParameterEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: AddedInletControlParameterEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${AddedOutletDrawingInformationEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: AddedOutletDrawingInformationEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${AddedPipeDrawingInformationEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: AddedPipeDrawingInformationEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${FlowPathDrawingInformationAllFieldsUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: FlowPathDrawingInformationAllFieldsUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${PipeDrawingInformationAllFieldsUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: PipeDrawingInformationAllFieldsUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${InletControlParameterAllFieldsUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: InletControlParameterAllFieldsUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${OutletDrawingInformationAllFieldUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: OutletDrawingInformationAllFieldUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${AddedFlowPathDrawingInformationEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: AddedFlowPathDrawingInformationEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["\${syc.kafka.topic.prefix}${OutletDrawingInformationElevationDataAllFieldsUpdatedEvent.EVENT_NAME}"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    override fun consume(event: OutletDrawingInformationElevationDataAllFieldsUpdatedEvent, ack: Acknowledgment) {
        eventHandler.on(event)
        ack.acknowledge()
    }
}
