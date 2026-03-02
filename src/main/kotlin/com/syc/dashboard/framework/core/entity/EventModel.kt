package com.syc.dashboard.framework.core.entity

import com.syc.dashboard.framework.core.events.BaseEvent
import java.util.*

abstract class EventModel(
    val timeStamp: Date? = null,
    val aggregateIdentifier: String = "",
    val aggregateType: String? = null,
    val version: Int? = null,
    val eventType: String? = null,
    val eventData: BaseEvent,
)
