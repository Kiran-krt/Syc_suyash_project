package com.syc.dashboard.command.notification.mobile.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.notification.mobile.entity.MobileNotificationAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.notification.mobile.events.MobileNotificationEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class MobileNotificationEventSourcingHandler @Autowired constructor(
    @Qualifier("mobileNotificationEventStore")
    private val mobileNotificationEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<MobileNotificationAggregate> {

    val aggregateCachingDays = 3L

    // caching to avoid replay of all events
    private val mobileNotificationAggregateCache: RMapCache<String, MobileNotificationAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_MOBILE_NOTIFICATION_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, MobileNotificationAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        mobileNotificationEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        mobileNotificationAggregateCache.fastPut(aggregate.id, aggregate as MobileNotificationAggregate)
    }

    override fun getById(id: String): MobileNotificationAggregate {
        val aggregateCache = mobileNotificationAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = MobileNotificationAggregate()

        val events = mobileNotificationEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        mobileNotificationAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = mobileNotificationEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = mobileNotificationEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(MobileNotificationEvents.MOBILE_NOTIFICATION_REPUBLISH_EVENTS, it) }
        }
    }
}
