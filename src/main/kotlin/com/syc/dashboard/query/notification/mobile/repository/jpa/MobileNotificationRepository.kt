package com.syc.dashboard.query.notification.mobile.repository.jpa

import com.syc.dashboard.query.notification.mobile.entity.MobileNotification
import org.springframework.data.mongodb.repository.MongoRepository

interface MobileNotificationRepository : MongoRepository<MobileNotification, String>
