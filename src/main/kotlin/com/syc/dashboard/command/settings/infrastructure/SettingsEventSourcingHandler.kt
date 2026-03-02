package com.syc.dashboard.command.settings.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.settings.entity.SettingsAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.settings.events.SettingsEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class SettingsEventSourcingHandler @Autowired constructor(
    @Qualifier("settingsEventStore")
    private val settingsEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<SettingsAggregate> {

    val aggregateCachingDays = 3L
    private val settingsAggregateCache: RMapCache<String, SettingsAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_SETTINGS_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, SettingsAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        settingsEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        settingsAggregateCache.fastPut(aggregate.id, aggregate as SettingsAggregate)
    }

    override fun getById(id: String): SettingsAggregate {
        val aggregateCache = settingsAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = SettingsAggregate()

        val events = settingsEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        settingsAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = settingsEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = settingsEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(SettingsEvents.SETTINGS_REPUBLISH_EVENTS, it) }
        }
    }
}
