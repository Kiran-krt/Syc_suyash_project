package com.syc.dashboard.framework.core.infrastructure

import com.syc.dashboard.framework.core.events.BaseEvent

interface EventHandler {
    fun <T : BaseEvent> on(event: T)
}
