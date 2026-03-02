package com.syc.dashboard.command.systemconfig.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.systemconfig.entity.SystemConfigAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.systemconfig.events.SystemConfigEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class SystemConfigEventSourcingHandler @Autowired constructor(
    @Qualifier("systemConfigEventStore")
    private val systemConfigEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<SystemConfigAggregate> {

    val aggregateCachingDays = 3L

    // caching to avoid replay of all events
    private val systemConfigAggregateCache: RMapCache<String, SystemConfigAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYSTEM_CONFIG_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, SystemConfigAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        systemConfigEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        systemConfigAggregateCache.fastPut(aggregate.id, aggregate as SystemConfigAggregate)
    }

    override fun getById(id: String): SystemConfigAggregate {
        val aggregateCache = systemConfigAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = SystemConfigAggregate()

        val events = systemConfigEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        systemConfigAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = systemConfigEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = systemConfigEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(SystemConfigEvents.SYSTEM_CONFIG_REPUBLISH_EVENTS, it) }
        }
    }
}
