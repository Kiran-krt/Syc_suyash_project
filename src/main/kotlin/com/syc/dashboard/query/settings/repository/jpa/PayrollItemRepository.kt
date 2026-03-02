package com.syc.dashboard.query.settings.repository.jpa

import com.syc.dashboard.query.settings.entity.PayrollItem
import org.springframework.data.mongodb.repository.MongoRepository

interface PayrollItemRepository : MongoRepository<PayrollItem, String>
