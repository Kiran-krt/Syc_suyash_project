package com.syc.dashboard.query.settings.repository.jpa

import com.syc.dashboard.query.settings.entity.VehicleInfo
import org.springframework.data.mongodb.repository.MongoRepository

interface VehicleInfoRepository : MongoRepository<VehicleInfo, String>
