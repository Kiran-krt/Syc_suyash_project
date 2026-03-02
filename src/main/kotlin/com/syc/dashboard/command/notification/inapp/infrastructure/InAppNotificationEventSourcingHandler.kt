package com.syc.dashboard.command.notification.inapp.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.notification.inapp.entity.InAppNotificationAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.notification.inapp.events.InAppNotificationEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class InAppNotificationEventSourcingHandler @Autowired constructor(
    @Qualifier("inAppNotificationEventStore")
    private val inAppNotificationEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<InAppNotificationAggregate> {

    val aggregateCachingDays = 3L

    // caching to avoid replay of all events
    private val inAppNotificationAggregateCache: RMapCache<String, InAppNotificationAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_IN_APP_NOTIFICATION_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, InAppNotificationAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        inAppNotificationEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        inAppNotificationAggregateCache.fastPut(aggregate.id, aggregate as InAppNotificationAggregate)
    }

    override fun getById(id: String): InAppNotificationAggregate {
        val aggregateCache = inAppNotificationAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = InAppNotificationAggregate()

        val events = inAppNotificationEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        inAppNotificationAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = inAppNotificationEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = inAppNotificationEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(InAppNotificationEvents.IN_APP_NOTIFICATION_REPUBLISH_EVENTS, it) }
        }
    }
}
