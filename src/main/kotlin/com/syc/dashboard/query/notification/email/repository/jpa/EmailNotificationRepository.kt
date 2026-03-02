package com.syc.dashboard.query.notification.email.repository.jpa

import com.syc.dashboard.query.notification.email.entity.EmailNotification
import org.springframework.data.mongodb.repository.MongoRepository

interface EmailNotificationRepository : MongoRepository<EmailNotification, String>
