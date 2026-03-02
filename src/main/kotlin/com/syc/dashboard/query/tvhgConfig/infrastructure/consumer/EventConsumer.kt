package com.syc.dashboard.query.tvhgConfig.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.tvhgconfig.events.*
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)

    fun consume(@Payload event: TvhgConfigRegisteredEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedUnitsInTvhgConfigEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedDesignStormTvhgConfigEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedStructureTypeInTvhgConfigEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedInletControlDataTvhgConfigEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedOutletStructureTypeTvhgConfigEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedPipeMaterialTvhgConfigEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedPipeTypeTvhgConfigEvent, ack: Acknowledgment)

    fun consume(@Payload event: DesignStormAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: InletControlDataAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: PipeMaterialAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: PipeTypeAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: StructureTypeAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: UnitsAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: OutletStructureTypeAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: AddedMdStandardNumberTvhgConfigEvent, ack: Acknowledgment)

    fun consume(@Payload event: MdStandardNumberAllFieldsUpdatedEvent, ack: Acknowledgment)
}
