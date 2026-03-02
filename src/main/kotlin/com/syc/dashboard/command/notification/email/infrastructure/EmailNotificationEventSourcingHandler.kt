package com.syc.dashboard.command.notification.email.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.notification.email.entity.EmailNotificationAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.notification.email.events.EmailNotificationEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class EmailNotificationEventSourcingHandler @Autowired constructor(
    @Qualifier("emailNotificationEventStore")
    private val emailNotificationEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<EmailNotificationAggregate> {

    val aggregateCachingDays = 3L

    // caching to avoid replay of all events
    private val emailNotificationAggregateCache: RMapCache<String, EmailNotificationAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_EMAIL_NOTIFICATION_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, EmailNotificationAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        emailNotificationEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        emailNotificationAggregateCache.fastPut(aggregate.id, aggregate as EmailNotificationAggregate)
    }

    override fun getById(id: String): EmailNotificationAggregate {
        val aggregateCache = emailNotificationAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = EmailNotificationAggregate()

        val events = emailNotificationEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        emailNotificationAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = emailNotificationEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = emailNotificationEventStore.getEvents(aggregateId)
            events.forEach {
                eventProducer.produceWithKafka(
                    EmailNotificationEvents.EMAIL_NOTIFICATION_REPUBLISH_EVENTS,
                    it,
                )
            }
        }
    }
}
