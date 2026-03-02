package com.syc.dashboard.query.notification.inapp.infrastructure.handler

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.notification.inapp.entity.InAppNotification
import com.syc.dashboard.query.notification.inapp.repository.jpa.InAppNotificationRepository
import com.syc.dashboard.shared.notification.inapp.events.InAppNotificationSentEvent
import com.syc.dashboard.shared.notification.inapp.events.InAppNotificationStatusUpdatedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InAppNotificationEventHandler @Autowired constructor(
    private val inAppNotificationRepository: InAppNotificationRepository,
) : EventHandler {

    private fun on(event: InAppNotificationSentEvent) {
        val inAppNotification = InAppNotification(
            id = event.id,
            userId = event.userId,
            message = event.message,
            status = event.status,
            creationDate = event.createdDate,
            userRole = event.userRole,
            objectId = event.objectId,
            objectType = event.objectType,
            eventType = event.eventType,
        ).buildEntity(event) as InAppNotification

        inAppNotificationRepository.save(inAppNotification)
    }
    private fun on(event: InAppNotificationStatusUpdatedEvent) {
        val inAppNotificationOptional = inAppNotificationRepository.findById(event.id)
        if (inAppNotificationOptional.isEmpty) {
            return
        }
        inAppNotificationOptional.get().status = event.status
        inAppNotificationRepository.save(inAppNotificationOptional.get())
    }
    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is InAppNotificationSentEvent -> on(event)
            is InAppNotificationStatusUpdatedEvent -> on(event)
        }
    }
}
