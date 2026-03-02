package com.syc.dashboard.command.employee.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.employee.entity.EmployeeAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.employee.events.EmployeeEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class EmployeeEventSourcingHandler @Autowired constructor(
    @Qualifier("employeeEventStore")
    private val employeeEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<EmployeeAggregate> {

    val aggregateCachingDays = 3L
    private val employeeAggregateCache: RMapCache<String, EmployeeAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_EMPLOYEE_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, EmployeeAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        employeeEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        employeeAggregateCache.fastPut(aggregate.id, aggregate as EmployeeAggregate)
    }

    override fun getById(id: String): EmployeeAggregate {
        val aggregateCache = employeeAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = EmployeeAggregate()

        val events = employeeEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        employeeAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = employeeEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = employeeEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(EmployeeEvents.EMPLOYEE_REPUBLISH_EVENTS, it) }
        }
    }
}
