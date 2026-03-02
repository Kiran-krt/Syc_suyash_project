package com.syc.dashboard.framework.core.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.syc.dashboard.framework.core.events.BaseEvent
import org.slf4j.LoggerFactory

abstract class AggregateRoot(
    var id: String = "",
    var version: Int = -1,
) {

    private val changes: MutableList<BaseEvent> = mutableListOf()

    @JsonIgnore
    val log = LoggerFactory.getLogger(javaClass)

    fun getUncommittedChanges(): List<BaseEvent> = changes

    fun markChangesAsCommitted() {
        changes.clear()
    }

    private fun applyChanges(event: BaseEvent, isNewEvent: Boolean) {
        try {
            val method = javaClass.getDeclaredMethod("apply", event.javaClass)
            method.isAccessible = true
            method.invoke(this, event)
        } catch (e: NoSuchMethodException) {
            log.warn("The apply method was not found in the aggregate for ${event.javaClass.name}")
        } catch (e: Exception) {
            log.error("Error applying event to aggregate", e)
        } finally {
            if (isNewEvent) {
                changes.add(event)
            }
        }
    }

    fun raiseEvent(event: BaseEvent) {
        applyChanges(event, true)
    }

    fun replayEvents(events: List<BaseEvent>) {
        events.forEach { applyChanges(it, false) }
    }
}
