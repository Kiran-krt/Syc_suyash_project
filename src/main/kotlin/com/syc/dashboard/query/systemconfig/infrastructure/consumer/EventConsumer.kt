package com.syc.dashboard.query.systemconfig.infrastructure.consumer

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.shared.systemconfig.events.SystemConfigAllFieldsUpdatedEvent
import com.syc.dashboard.shared.systemconfig.events.SystemConfigLogoUpdatedEvent
import com.syc.dashboard.shared.systemconfig.events.SystemConfigRegisteredEvent
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload

interface EventConsumer {

    fun consume(@Payload event: SystemConfigRegisteredEvent, ack: Acknowledgment)

    fun consume(@Payload event: BaseEvent, ack: Acknowledgment)

    fun consume(@Payload event: SystemConfigAllFieldsUpdatedEvent, ack: Acknowledgment)

    fun consume(@Payload event: SystemConfigLogoUpdatedEvent, ack: Acknowledgment)
}
