package com.syc.dashboard.framework.core.producers

import com.syc.dashboard.framework.core.events.BaseEvent

interface EventProducer {
    fun produceWithKafka(topic: String, event: BaseEvent)
    fun produceWithRedis(topic: String, event: BaseEvent)
}
