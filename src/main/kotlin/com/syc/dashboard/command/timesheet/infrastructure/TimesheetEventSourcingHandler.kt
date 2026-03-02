package com.syc.dashboard.command.timesheet.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.timesheet.entity.TimesheetAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.timesheet.events.TimesheetEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class TimesheetEventSourcingHandler @Autowired constructor(
    @Qualifier("timesheetEventStore")
    private val timesheetEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<TimesheetAggregate> {

    val aggregateCachingDays = 3L

    private val timesheetAggregateCache: RMapCache<String, TimesheetAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_TIMESHEET_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, TimesheetAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        timesheetEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        timesheetAggregateCache.fastPut(aggregate.id, aggregate as TimesheetAggregate)
    }

    override fun getById(id: String): TimesheetAggregate {
        val aggregateCache = timesheetAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = TimesheetAggregate()

        val events = timesheetEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        timesheetAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = timesheetEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = timesheetEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(TimesheetEvents.TIMESHEET_REPUBLISH_EVENTS, it) }
        }
    }
}
