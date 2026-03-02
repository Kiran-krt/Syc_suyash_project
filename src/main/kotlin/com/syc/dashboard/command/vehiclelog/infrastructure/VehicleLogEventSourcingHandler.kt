package com.syc.dashboard.command.vehiclelog.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.syc.dashboard.command.vehiclelog.entity.VehicleLogAggregate
import com.syc.dashboard.framework.common.cache.RedisCacheConstants
import com.syc.dashboard.framework.core.entity.AggregateRoot
import com.syc.dashboard.framework.core.handlers.EventSourcingHandler
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import com.syc.dashboard.shared.vehiclelog.events.VehicleLogEvents
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class VehicleLogEventSourcingHandler @Autowired constructor(
    @Qualifier("vehicleLogEventStore")
    private val vehicleLogEventStore: EventStore,
    private val eventProducer: EventProducer,
    redissonClient: RedissonClient,
) : EventSourcingHandler<VehicleLogAggregate> {

    val aggregateCachingDays = 3L
    private val vehicleLogAggregateCache: RMapCache<String, VehicleLogAggregate> =
        redissonClient.getMapCache(
            RedisCacheConstants.SYC_VEHICLE_LOG_AGGREGATE,
            TypedJsonJacksonCodec(String::class.java, VehicleLogAggregate::class.java, jacksonObjectMapper()),
        )

    override fun save(aggregate: AggregateRoot) {
        vehicleLogEventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), aggregate.version)

        // caching
        aggregate.version = aggregate.version + aggregate.getUncommittedChanges().size
        aggregate.markChangesAsCommitted()
        vehicleLogAggregateCache.fastPut(aggregate.id, aggregate as VehicleLogAggregate)
    }

    override fun getById(id: String): VehicleLogAggregate {
        val aggregateCache = vehicleLogAggregateCache[id]
        if (aggregateCache != null) return aggregateCache

        val aggregate = VehicleLogAggregate()

        val events = vehicleLogEventStore.getEvents(id)
        if (events.isNotEmpty()) {
            aggregate.replayEvents(events)
            val latestVersion = events.stream().map { it.version }.max(Comparator.naturalOrder())
            aggregate.version = latestVersion.get()
        }
        vehicleLogAggregateCache.fastPut(id, aggregate, aggregateCachingDays, TimeUnit.DAYS)
        return aggregate
    }

    override fun republishEvents() {
        val aggregateIds = vehicleLogEventStore.getAggregateIds()
        for (aggregateId in aggregateIds) {
            val aggregate = getById(aggregateId)

            if (!aggregate.active) continue

            val events = vehicleLogEventStore.getEvents(aggregateId)
            events.forEach { eventProducer.produceWithKafka(VehicleLogEvents.VEHICLE_LOG_REPUBLISH_EVENTS, it) }
        }
    }
}
