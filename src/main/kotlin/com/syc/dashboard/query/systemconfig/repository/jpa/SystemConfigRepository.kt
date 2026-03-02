package com.syc.dashboard.query.systemconfig.repository.jpa

import com.syc.dashboard.query.systemconfig.entity.SystemConfig
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SystemConfigRepository : MongoRepository<SystemConfig, String>
