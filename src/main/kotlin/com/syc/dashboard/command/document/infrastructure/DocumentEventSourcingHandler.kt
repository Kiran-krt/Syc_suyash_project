package com.syc.dashboard.command.document.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.document.entity.DocumentAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.document.events.DocumentEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class DocumentEventSourcingHandler @Autowired constructor(
    @Qualifier("documentEventStore")
    private val documentEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<DocumentAggregate> {

    val aggregateCachingDays = 3L
    private val documentAggregateCache: RMapCache<String, DocumentAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_DOCUMENT_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, DocumentAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        documentEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        documentAggregateCache.fastPut(aggregate.id, aggregate as DocumentAggregate)
    }

    override fun getById(id: String): DocumentAggregate {
        val aggregateCache = documentAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = DocumentAggregate()

        val events = documentEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        documentAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = documentEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = documentEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(DocumentEvents.DOCUMENT_REPUBLISH_EVENTS, it) }
        }
    }
}
