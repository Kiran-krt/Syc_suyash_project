package com.syc.dashboard.command.expensereport.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.expensereport.entity.ExpenseReportAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.expensereport.events.ExpenseReportEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class ExpenseReportEventSourcingHandler @Autowired constructor(
    @Qualifier("expenseReportEventStore")
    private val expenseReportEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<ExpenseReportAggregate> {

    val aggregateCachingDays = 3L
    private val settingsAggregateCache: RMapCache<String, ExpenseReportAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_EXPENSE_REPORT_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, ExpenseReportAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        expenseReportEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        settingsAggregateCache.fastPut(aggregate.id, aggregate as ExpenseReportAggregate)
    }

    override fun getById(id: String): ExpenseReportAggregate {
        val aggregateCache = settingsAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = ExpenseReportAggregate()

        val events = expenseReportEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        settingsAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = expenseReportEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = expenseReportEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(ExpenseReportEvents.EXPENSE_REPORT_REPUBLISH_EVENTS, it) }
        }
    }
}
