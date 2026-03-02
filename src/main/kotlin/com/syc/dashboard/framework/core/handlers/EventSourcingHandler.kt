package com.syc.dashboard.framework.core.handlers

import com.syc.dashboard.framework.core.entity.AggregateRoot

interface EventSourcingHandler<T> {
    fun save(aggregate: AggregateRoot)
    fun getById(id: String): T
    fun republishEvents()
}
