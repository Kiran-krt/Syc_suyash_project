package com.syc.dashboard.command.project.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.project.entity.ProjectAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.project.events.ProjectEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class ProjectEventSourcingHandler @Autowired constructor(
    @Qualifier("projectEventStore")
    private val projectEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<ProjectAggregate> {

    val aggregateCachingDays = 3L

    // caching to avoid replay of all events
    private val projectAggregateCache: RMapCache<String, ProjectAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_PROJECT_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, ProjectAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        projectEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        projectAggregateCache.fastPut(aggregate.id, aggregate as ProjectAggregate)
    }

    override fun getById(id: String): ProjectAggregate {
        val aggregateCache = projectAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = ProjectAggregate()

        val events = projectEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        projectAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = projectEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = projectEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(ProjectEvents.PROJECT_REPUBLISH_EVENTS, it) }
        }
    }
}
