package com.syc.dashboard.query.notification.mobile.infrastructure.handler

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.notification.mobile.entity.MobileNotification
import com.syc.dashboard.query.notification.mobile.repository.jpa.MobileNotificationRepository
import com.syc.dashboard.shared.notification.mobile.events.MobileNotificationSentEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MobileNotificationEventHandler @Autowired constructor(
    private val mobileNotificationRepository: MobileNotificationRepository,
) : EventHandler {

    private fun on(event: MobileNotificationSentEvent) {
        val mobileNotification = MobileNotification(
            id = event.id,
            userId = event.userId,
            message = event.message,
            status = event.status,
            creationDate = event.createdDate,
            userRole = event.userRole,
            objectId = event.objectId,
            objectType = event.objectType,
            eventType = event.eventType,
        ).buildEntity(event) as MobileNotification

        mobileNotificationRepository.save(mobileNotification)
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is MobileNotificationSentEvent -> on(event)
        }
    }
}
