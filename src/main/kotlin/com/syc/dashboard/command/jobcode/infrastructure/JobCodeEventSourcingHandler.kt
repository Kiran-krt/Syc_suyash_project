package com.syc.dashboard.command.jobcode.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.jobcode.entity.JobCodeAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.jobcode.events.JobCodeEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class JobCodeEventSourcingHandler @Autowired constructor(
    @Qualifier("jobCodeEventStore")
    private val jobCodeEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<JobCodeAggregate> {

    val aggregateCachingDays = 3L

    // caching to avoid replay of all events
    private val jobCodeAggregateCache: RMapCache<String, JobCodeAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_JOBCODE_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, JobCodeAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        jobCodeEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        jobCodeAggregateCache.fastPut(aggregate.id, aggregate as JobCodeAggregate)
    }

    override fun getById(id: String): JobCodeAggregate {
        val aggregateCache = jobCodeAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = JobCodeAggregate()

        val events = jobCodeEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        jobCodeAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = jobCodeEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = jobCodeEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(JobCodeEvents.JOBCODE_REPUBLISH_EVENTS, it) }
        }
    }
}
