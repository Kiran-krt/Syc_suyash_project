package com.syc.dashboard.query.tvhginput.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.tvhginput.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)

    fun consume(@Payload event: TvhgInputRegisteredEvent, ack: Acknowledgment)

    fun consume(@Payload event: TvhgInputStatusUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: TvhgInputAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedProjectInformationInTvhgInputEvent, ack: Acknowledgment)

    fun consume(@Payload event: StructureInformationAddedEvent, ack: Acknowledgment)

    fun consume(@Payload event: StructureInformationAllFieldUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: ProjectInformationAllFieldUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: HydrologicInformationAddedEvent, ack: Acknowledgment)

    fun consume(@Payload event: HydrologicInformationAllFieldUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedPipeInformationTvhgInputEvent, ack: Acknowledgment)

    fun consume(@Payload event: StructureDrawingDataAddedEvent, ack: Acknowledgment)

    fun consume(@Payload event: PipeInformationAllFieldUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: StructureDrawingDataAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedInletControlParameterEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedOutletDrawingInformationEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedPipeDrawingInformationEvent, ack: Acknowledgment)

    fun consume(@Payload event: FlowPathDrawingInformationAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: PipeDrawingInformationAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: InletControlParameterAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: OutletDrawingInformationAllFieldUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedFlowPathDrawingInformationEvent, ack: Acknowledgment)

    fun consume(@Payload event: OutletDrawingInformationElevationDataAllFieldsUpdatedEvent, ack: Acknowledgment)
}
