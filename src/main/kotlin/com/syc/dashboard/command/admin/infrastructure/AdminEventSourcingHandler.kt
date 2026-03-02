package com.syc.dashboard.command.admin.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.admin.entity.AdminAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.admin.events.AdminEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AdminEventSourcingHandler @Autowired constructor(
    @Qualifier("adminEventStore")
    private val adminEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<AdminAggregate> {

    val aggregateCachingDays = 3L

    // caching to avoid replay of all events
    private val adminAggregateCache: RMapCache<String, AdminAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_ADMIN_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, AdminAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        adminEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        adminAggregateCache.fastPut(aggregate.id, aggregate as AdminAggregate)
    }

    override fun getById(id: String): AdminAggregate {
        val aggregateCache = adminAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = AdminAggregate()

        val events = adminEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        adminAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = adminEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = adminEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(AdminEvents.ADMIN_REPUBLISH_EVENTS, it) }
        }
    }
}
