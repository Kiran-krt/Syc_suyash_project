package com.syc.dashboard.command.projectreport.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.projectreport.entity.ProjectReportAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.projectreport.events.ProjectReportEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class ProjectReportEventSourcingHandler @Autowired constructor(
    @Qualifier("projectReportEventStore")
    private val projectReportEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<ProjectReportAggregate> {

    val aggregateCachingDays = 3L
    private val settingsAggregateCache: RMapCache<String, ProjectReportAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_PROJECT_REPORT_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, ProjectReportAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        projectReportEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        settingsAggregateCache.fastPut(aggregate.id, aggregate as ProjectReportAggregate)
    }

    override fun getById(id: String): ProjectReportAggregate {
        val aggregateCache = settingsAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = ProjectReportAggregate()

        val events = projectReportEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        settingsAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = projectReportEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = projectReportEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(ProjectReportEvents.PROJECT_REPORT_REPUBLISH_EVENTS, it) }
        }
    }
}
