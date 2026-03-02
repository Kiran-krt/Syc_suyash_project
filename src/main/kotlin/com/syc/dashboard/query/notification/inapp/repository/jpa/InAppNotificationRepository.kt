package com.syc.dashboard.query.notification.inapp.repository.jpa

import com.syc.dashboard.query.notification.inapp.entity.InAppNotification
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface InAppNotificationRepository : MongoRepository<InAppNotification, String>
