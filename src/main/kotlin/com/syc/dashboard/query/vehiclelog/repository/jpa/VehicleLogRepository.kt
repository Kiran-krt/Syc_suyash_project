package com.syc.dashboard.query.vehiclelog.repository.jpa

import com.syc.dashboard.query.vehiclelog.entity.VehicleLog
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleLogRepository : MongoRepository<VehicleLog, String>
