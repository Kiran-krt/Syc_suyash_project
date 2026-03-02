package com.syc.dashboard.command.notification.email.repository.jpa

import com.syc.dashboard.command.notification.email.entity.EmailNotificationEventModel
import org.springframework.data.mongodb.repository.MongoRepository

interface EmailNotificationEventStoreRepository : MongoRepository<EmailNotificationEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<EmailNotificationEventModel>
}
