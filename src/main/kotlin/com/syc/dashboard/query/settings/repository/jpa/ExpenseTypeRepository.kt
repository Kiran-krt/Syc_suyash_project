package com.syc.dashboard.query.settings.repository.jpa

import com.syc.dashboard.query.settings.entity.ExpenseType
import org.springframework.data.mongodb.repository.MongoRepository

interface ExpenseTypeRepository : MongoRepository<ExpenseType, String>
