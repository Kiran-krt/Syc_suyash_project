package com.syc.dashboard.framework.core.infrastructure

import com.syc.dashboard.framework.core.events.BaseEvent

interface EventStore {
    fun saveEvents(aggregateId: String, events: Iterable<BaseEvent>, expectedVersion: Int)
    fun getEvents(aggregateId: String): List<BaseEvent>
    fun getAggregateIds(): List<String>
}
