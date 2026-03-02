package com.syc.dashboard.command.tvhginput.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.tvhginput.entity.TvhgInputAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.tvhginput.events.TvhgInputEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class TvhgInputEventSourcingHandler @Autowired constructor(
    @Qualifier("tvhgInputEventStore")
    private val tvhgInputEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<TvhgInputAggregate> {

    val aggregateCachingDays = 3L
    private val tvhgAggregateCache: RMapCache<String, TvhgInputAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_TVHG_INPUT_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, TvhgInputAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        tvhgInputEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        tvhgAggregateCache.fastPut(aggregate.id, aggregate as TvhgInputAggregate)
    }

    override fun getById(id: String): TvhgInputAggregate {
        val aggregateCache = tvhgAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = TvhgInputAggregate()

        val events = tvhgInputEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        tvhgAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = tvhgInputEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = tvhgInputEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(TvhgInputEvents.TVHG_REPUBLISH_EVENTS, it) }
        }
    }
}
