package com.syc.dashboard.command.systemconfig.infrastructure

import com.syc.dashboard.command.systemconfig.entity.SystemConfigEventModel
import com.syc.dashboard.command.systemconfig.exceptions.SystemConfigEventStreamNotExistInEventStoreException
import com.syc.dashboard.command.systemconfig.repository.jpa.SystemConfigEventStoreRepository
import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.exceptions.AggregateNotFoundException
import com.syc.dashboard.framework.core.exceptions.ConcurrencyException
import com.syc.dashboard.framework.core.infrastructure.EventStore
import com.syc.dashboard.framework.core.producers.EventProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class SystemConfigEventStore @Autowired constructor(
    private val eventProducer: EventProducer,
    private val eventStoreRepository: SystemConfigEventStoreRepository,
) : EventStore {

    override fun saveEvents(aggregateId: String, events: Iterable<BaseEvent>, expectedVersion: Int) {
        val eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId)
        if (expectedVersion != -1 && eventStream[eventStream.size - 1].version != expectedVersion) {
            throw ConcurrencyException()
        }

        var version = expectedVersion
        events
            .forEach {
                version++
                it.version = version
                val eventModel = SystemConfigEventModel(
                    timeStamp = Date(),
                    aggregateIdentifier = aggregateId,
                    version = version,
                    eventType = it.javaClass.typeName,
                    eventData = it,
                )
                val persistedEvent = eventStoreRepository.save(eventModel)
                if (persistedEvent.id?.isNotEmpty() == true) {
                    eventProducer.produceWithKafka(it.javaClass.simpleName, it)
                }
            }
    }

    override fun getEvents(aggregateId: String): List<BaseEvent> {
        val eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId)
        if (eventStream.isEmpty()) {
            throw AggregateNotFoundException("Incorrect ID '$aggregateId' provided.")
        }
        return eventStream.stream().map { it.eventData }.collect(Collectors.toList())
    }

    override fun getAggregateIds(): List<String> {
        // TODO: change this to get ids by group by clause on aggregateIdentifier
        val eventStream = eventStoreRepository.findAll()

        if (eventStream.isEmpty()) {
            throw SystemConfigEventStreamNotExistInEventStoreException(
                "Could not retrieve system config event stream from the event store!",
            )
        }

        return eventStream.stream().map { it.aggregateIdentifier }.distinct().toList()
    }
}
