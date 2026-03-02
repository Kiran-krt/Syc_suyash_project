package com.syc.dashboard.command.jobcode.entity

import com.syc.dashboard.framework.core.entity.EventModel
import com.syc.dashboard.framework.core.events.BaseEvent
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "c_jobCodeEventModel")
class JobCodeEventModel(
    @Id
    var id: String? = null,
    timeStamp: Date?,
    aggregateIdentifier: String = "",
    aggregateType: String? = null,
    version: Int? = null,
    eventType: String? = null,
    eventData: BaseEvent,
) : EventModel(
    timeStamp = timeStamp,
    aggregateIdentifier = aggregateIdentifier,
    aggregateType = aggregateType,
    version = version,
    eventType = eventType,
    eventData = eventData,
)
