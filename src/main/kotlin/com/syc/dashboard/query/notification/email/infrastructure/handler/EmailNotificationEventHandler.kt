package com.syc.dashboard.query.notification.email.infrastructure.handler

import com.syc.dashboard.framework.core.events.BaseEvent
import com.syc.dashboard.framework.core.infrastructure.EventHandler
import com.syc.dashboard.query.notification.email.entity.EmailNotification
import com.syc.dashboard.query.notification.email.repository.jpa.EmailNotificationRepository
import com.syc.dashboard.shared.notification.email.events.EmailNotificationSentEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmailNotificationEventHandler @Autowired constructor(
    private val emailNotificationRepository: EmailNotificationRepository,
) : EventHandler {

    private fun on(event: EmailNotificationSentEvent) {
        val emailNotification = EmailNotification(
            id = event.id,
            userId = event.userId,
            subject = event.subject,
            message = event.message,
            status = event.status,
            creationDate = event.createdDate,
            userRole = event.userRole,
            objectId = event.objectId,
            objectType = event.objectType,
            eventType = event.eventType,
        ).buildEntity(event) as EmailNotification

        emailNotificationRepository.save(emailNotification)
    }

    override fun <T : BaseEvent> on(event: T) {
        when (event) {
            is EmailNotificationSentEvent -> on(event)
        }
    }
}
