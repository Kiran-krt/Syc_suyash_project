package com.syc.dashboard.framework.common.infrastructure

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.producers.EventProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class EventProducerImpl @Autowired constructor(
    private val redisTemplate: ReactiveRedisTemplate<String, Any>,
    @Value("\${syc.redis.topic.prefix:syc-}")
    private val REDIS_TOPIC_PREFIX: String,

    private val kafkaTemplate: KafkaTemplate<String, Any>,
    @Value("\${syc.kafka.topic.prefix:syc-}")
    private val KAFKA_TOPIC_PREFIX: String,

) : EventProducer {

    override fun produceWithKafka(topic: String, event: BaseEvent) {
        kafkaTemplate.send("${KAFKA_TOPIC_PREFIX}$topic", event)
    }

    override fun produceWithRedis(topic: String, event: BaseEvent) {
        redisTemplate.convertAndSend("${REDIS_TOPIC_PREFIX}$topic", event)
            .subscribe()
    }
}
