package com.syc.dashboard.command.notification.mobile.repository.jpa

import com.syc.dashboard.command.notification.mobile.entity.MobileNotificationEventModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MobileNotificationEventStoreRepository : MongoRepository<MobileNotificationEventModel, String> {

    fun findByAggregateIdentifier(aggregateIdentifier: String): List<MobileNotificationEventModel>
}
