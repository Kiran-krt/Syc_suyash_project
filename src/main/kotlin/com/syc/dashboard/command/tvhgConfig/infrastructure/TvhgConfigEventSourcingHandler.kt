package com.syc.dashboard.command.tvhgConfig.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.tvhgConfig.entity.TvhgConfigAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.tvhgconfig.events.TvhgConfigEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class TvhgConfigEventSourcingHandler @Autowired constructor(
    @Qualifier("tvhgConfigEventStore")
    private val tvhgConfigEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<TvhgConfigAggregate> {

    val aggregateCachingDays = 3L
    private val tvhgConfigAggregateCache: RMapCache<String, TvhgConfigAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_TVHG_CONFIG_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, TvhgConfigAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        tvhgConfigEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        tvhgConfigAggregateCache.fastPut(aggregate.id, aggregate as TvhgConfigAggregate)
    }

    override fun getById(id: String): TvhgConfigAggregate {
        val aggregateCache = tvhgConfigAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = TvhgConfigAggregate()

        val events = tvhgConfigEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        tvhgConfigAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = tvhgConfigEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = tvhgConfigEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(TvhgConfigEvents.TVHG_CONFIG_REPUBLISH_EVENTS, it) }
        }
    }
}
