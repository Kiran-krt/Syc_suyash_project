package com.syc.dashboard.framework.core.events

import com.syc.dashboard.framework.core.messages.Message

abstract class BaseEvent(
    id: String,
    var version: Int,
    var auditTrail: Boolean,
) : Message(id = id)
