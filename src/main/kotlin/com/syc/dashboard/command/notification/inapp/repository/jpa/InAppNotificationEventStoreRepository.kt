package com.syc.dashboard.command.notification.inapp.repository.jpa

import com.syc.dashboard.command.notification.inapp.entity.InAppNotificationEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface InAppNotificationEventStoreRepository : MongoRepository<InAppNotificationEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<InAppNotificationEventModel>
}
